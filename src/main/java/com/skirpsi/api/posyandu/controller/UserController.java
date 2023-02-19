package com.skirpsi.api.posyandu.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.entity.intfc.UserInterface;
import com.skirpsi.api.posyandu.service.UserService;
import com.skirpsi.api.posyandu.service.WhatsappService;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired UserService userServ;
	
	@Autowired WhatsappService whatsServ;
	
	@GetMapping("/pass/all")
	public ResponseEntity<List<UserPosyandu>> getAllUser(){
		List<UserPosyandu> all = userServ.getAll();
		
		if(all.size()>0) {
			return new ResponseEntity<>(all,HttpStatus.OK);	
		}else {
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@GetMapping("/pass/{id}")
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
		System.out.println(user.getUserType());
		
		user.setPasswordUser("INI PASSWORD DEFAULT");
		
		if(userServ.checkIfExistByPhone(user.getNoTeleponUser())) {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT); 
		}
		
		String defPass = simpleDateFormat.format(user.getTanggalLahirUser());
		
		UserPosyandu newUser = new UserPosyandu();
		newUser.setAlamatUser(user.getAlamatUser());
		newUser.setNamaUser(user.getNamaUser());
		newUser.setNoTeleponUser(user.getNoTeleponUser());
		newUser.setPasswordUser(encoder.encode(defPass));
		newUser.setUserType(user.getUserType());
		newUser.setNikUser(user.getNikUser());
		newUser.setTanggalLahirUser(user.getTanggalLahirUser());
		
		UserPosyandu x = userServ.insert(newUser);
		
		if(x==null) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			return new ResponseEntity<>(x,HttpStatus.OK);
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
	    	_user.setUserType(user.getUserType());
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
	
//	@PostMapping("/login")
//	public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
//
//	    Authentication authentication = authenticationManager.authenticate(
//	            new UsernamePasswordAuthenticationToken(
//	                    loginRequest.getUsernameOrEmail(),
//	                    loginRequest.getPassword()
//	            )
//	    );
//
//	    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//	    String jwt = tokenProvider.generateToken(authentication);
//	    return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
//	}'
	
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
//		whatsServ.testSendAPI();
//		System.out.println(user.getNamaUser());
//		System.out.println(user.getNoTeleponUser());
		whatsServ.sendPassword(user);
		
		return new ResponseEntity<>("GOOD",HttpStatus.OK);
	}
	  
}
