package com.skirpsi.api.posyandu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	
	
	@Autowired UserService userServ;
	
	@GetMapping("/all")
	public ResponseEntity<List<UserPosyandu>> getAllUser(){
		List<UserPosyandu> all = userServ.getAll();
		
		if(all.size()>0) {
			return new ResponseEntity<>(all,HttpStatus.OK);	
		}else {
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@GetMapping("/{id}")
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
		
		UserPosyandu x = userServ.insert(user);
		
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
	    	_user.setNik(user.getNik());
	    	_user.setTanggalLahir(user.getTanggalLahir());
	    	
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
//	}

	  
}