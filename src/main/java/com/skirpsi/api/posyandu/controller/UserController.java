package com.skirpsi.api.posyandu.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skirpsi.api.posyandu.entity.ERole;
import com.skirpsi.api.posyandu.entity.Role;
import com.skirpsi.api.posyandu.entity.User;
import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.entity.intfc.UserInterface;
import com.skirpsi.api.posyandu.payload.request.LoginRequest;
import com.skirpsi.api.posyandu.repository.RoleRepository;
import com.skirpsi.api.posyandu.repository.UserRepository;
import com.skirpsi.api.posyandu.security.jwt.JwtUtils;
import com.skirpsi.api.posyandu.security.services.UserDetailsImpl;
import com.skirpsi.api.posyandu.service.UserPosyanduService;
import com.skirpsi.api.posyandu.service.WhatsappService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired 
	UserPosyanduService userServ;
	
	@Autowired 
	WhatsappService whatsServ;
	
	@PostMapping()
	public ResponseEntity<UserPosyandu> createUser(@RequestBody UserPosyandu user){
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		if(userServ.checkIfExistByPhone(user.getNoTeleponUser())) {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT); 
		}
		
		String defPass = simpleDateFormat.format(user.getTanggalLahirUser());
		
		UserPosyandu newUser = new UserPosyandu();
		newUser.setAlamatUser(user.getAlamatUser());
		newUser.setNamaUser(user.getNamaUser());
		newUser.setNoTeleponUser(user.getNoTeleponUser());
		newUser.setPasswordUser(encoder.encode(defPass));
		newUser.setTipeUser(user.getTipeUser());
		newUser.setNikUser(user.getNikUser());
		newUser.setTanggalLahirUser(user.getTanggalLahirUser());
		
		UserPosyandu x = userServ.insert(newUser);
//		whatsServ.sendPassword(x);
		if(x==null) {
			userServ.delete(newUser.getIdUser());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			User userReq = new User(x.getNoTeleponUser(),
					 encoder.encode(defPass),x.getIdUser());

			Set<Role> roles = new HashSet<>();
			if(x.getTipeUser()==0) {
				Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(adminRole);
			}else if(x.getTipeUser()==1) {
				Role modRole = roleRepository.findByName(ERole.ROLE_PETUGAS)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(modRole);
			}else {
				Role userRole = roleRepository.findByName(ERole.ROLE_ORANGTUA)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			}
			userReq.setRoles(roles);
			userRepository.save(userReq);
			return new ResponseEntity<>(x,HttpStatus.OK);
		}
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
//		System.out.println(loginRequest.getnotelepon());
//		System.out.println(loginRequest.getPassword());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getnotelepon(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();	
//		System.out.println("Login : " + userDetails.getIdUser());
		UserPosyandu retUser = userServ.getOneById(userDetails.getIdUser());
		ObjectMapper oMapper = new ObjectMapper();
		
		@SuppressWarnings("unchecked")
		Map<String, Object> result = oMapper.convertValue(retUser, Map.class);
		if(result==null) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}else {
			result.put("accessToken", jwt);
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date d = new Date(Long.parseLong(result.get("tanggalLahirUser").toString()));
			String date = simpleDateFormat.format(d);
			result.remove("passwordUser");
			result.remove("tanggalLahirUser");
			result.put("tanggalLahirUser", date);
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
		
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserPosyandu user,@PathVariable("id") Integer id){
		UserPosyandu _user = userServ.getOneById(id);
		if(_user==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			_user.setAlamatUser(user.getAlamatUser());
	    	_user.setNamaUser(user.getNamaUser());
	    	_user.setNoTeleponUser(user.getNoTeleponUser());
	    	_user.setTipeUser(user.getTipeUser());
	    	_user.setNikUser(user.getNikUser());
	    	_user.setTanggalLahirUser(user.getTanggalLahirUser());
	    	
	    	userServ.insert(_user);
	    	ObjectMapper oMapper = new ObjectMapper();
	    	@SuppressWarnings("unchecked")
			Map<String, Object> result = oMapper.convertValue(_user, Map.class);
	    	result.remove("passwordUser");
	    	String pattern = "dd-MM-yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date d = new Date(Long.parseLong(result.get("tanggalLahirUser").toString()));
			String date = simpleDateFormat.format(d);
			result.remove("tanggalLahirUser");
			result.put("tanggalLahirUser", date);
	    	return new ResponseEntity<>(result,HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") Integer id){
		UserPosyandu x = userServ.delete(id);
		
		if(x==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	@GetMapping("/all")
	public ResponseEntity<List<Map<String, Object>>> getAllUserWithoutPassword(){
		List<UserInterface> data = userServ.getAllWithoutPassword();
		
		if(data==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			ObjectMapper oMapper = new ObjectMapper();
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> result = oMapper.convertValue(data, List.class);
			List<Map<String, Object>> res = new ArrayList<>();
			for (Map<String, Object> x : result) {
				String pattern = "yyyy-MM-dd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				Date d = new Date(Long.parseLong( x.get("tanggalLahirUser").toString()));
				String date = simpleDateFormat.format(d);
				x.remove("tanggalLahirUser");
				x.put("tanggalLahirUser", date);
				res.add(x);
			}
			return new ResponseEntity<>(res,HttpStatus.OK);	
		}
		
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getOneWithourPaswword(@PathVariable("id") Integer id){
		UserInterface data = userServ.getOneByIdWithoutPassword(id);
		
		if(data==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			ObjectMapper oMapper = new ObjectMapper();
			@SuppressWarnings("unchecked")
			Map<String, Object> result = oMapper.convertValue(data, Map.class);
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date d = new Date(Long.parseLong(result.get("tanggalLahirUser").toString()));
			String date = simpleDateFormat.format(d);
			result.remove("tanggalLahirUser");
			result.put("tanggalLahirUser", date);
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
	}
	
	@GetMapping("/wa")
	public ResponseEntity<String> sendPasswordThruWa(@RequestBody UserPosyandu user){
		whatsServ.sendPassword(user);
		
		return new ResponseEntity<>("GOOD",HttpStatus.OK);
	}
	  
}
