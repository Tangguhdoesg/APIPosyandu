package com.skirpsi.api.posyandu.controller;

import java.text.SimpleDateFormat;
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
import com.skirpsi.api.posyandu.service.UserService;
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
	UserService userServ;
	
	@Autowired 
	WhatsappService whatsServ;
	
	@GetMapping("/pass/all") //unused
	public ResponseEntity<List<UserPosyandu>> getAllUser(){
		List<UserPosyandu> all = userServ.getAll();
		
		if(all.size()>0) {
			return new ResponseEntity<>(all,HttpStatus.OK);	
		}else {
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@GetMapping("/pass/{id}") //unused
	public ResponseEntity<UserPosyandu> getById(@PathVariable("id") Integer id){
		UserPosyandu data = userServ.getOneById(id);
		
		if(!(data==null)) {
			return new ResponseEntity<>(data,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping()
	public ResponseEntity<UserPosyandu> createUser(@RequestBody UserPosyandu user){
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		System.out.println(user.getNamaUser());
		System.out.println(user.getPasswordUser());
		System.out.println(user.getTanggalLahirUser());
		System.out.println(user.getNoTeleponUser());
		System.out.println(user.getAlamatUser());
		System.out.println(user.getTipeUser());
		
		if(userServ.checkIfExistByPhone(user.getNoTeleponUser())) {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT); 
		}
		
		String defPass = simpleDateFormat.format(user.getTanggalLahirUser());
		
		System.out.println("Def Pass : " + defPass);
		
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
			userServ.delete(x.getIdUser());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			User userReq = new User(x.getNoTeleponUser(),
					 encoder.encode(defPass));

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
	public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest loginRequest){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getnotelepon(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		UserPosyandu retUser = userServ.getOneById(userDetails.getId().intValue());
		ObjectMapper oMapper = new ObjectMapper();
		System.out.println(retUser.getTanggalLahirUser());
		
		@SuppressWarnings("unchecked")
		Map<String, Object> result = oMapper.convertValue(retUser, Map.class);
		System.out.println(userDetails.getId());
		if(result==null) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}else {
			result.put("accessToken", jwt);
			String pattern = "dd-MM-yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date d = new Date(Long.parseLong(result.get("tanggalLahirUser").toString()));
			String date = simpleDateFormat.format(d);
			System.out.println(date);
			result.remove("passwordUser");
			result.remove("tanggalLahirUser");
			result.put("tanggalLahirUser", date);
			result.put("integer", 4);
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
		
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<UserPosyandu> updateUser(@RequestBody UserPosyandu user,@PathVariable("id") Integer id){
		UserPosyandu _user = userServ.getOneById(id);
		
		if(_user==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			_user.setAlamatUser(user.getAlamatUser());
	    	_user.setNamaUser(user.getNamaUser());
	    	_user.setNoTeleponUser(user.getNoTeleponUser());
	    	_user.setPasswordUser(user.getPasswordUser());
	    	_user.setTipeUser(user.getTipeUser());
	    	_user.setNikUser(user.getNikUser());
	    	_user.setTanggalLahirUser(user.getTanggalLahirUser());
	    	
	    	userServ.insert(_user);
	    	return new ResponseEntity<>(_user,HttpStatus.OK);
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
	public ResponseEntity<List<UserInterface>> getAllUserWithoutPassword(){
		List<UserInterface> data = userServ.getAllWithoutPassword();
		
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserInterface> getOneWithourPaswword(@PathVariable("id") Integer id){
		UserInterface data = userServ.getOneByIdWithoutPassword(id);
		
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
	
	@GetMapping("/wa")
	public ResponseEntity<String> testWa(@RequestBody UserPosyandu user){
		whatsServ.sendPassword(user);
		
		return new ResponseEntity<>("GOOD",HttpStatus.OK);
	}
	  
}
