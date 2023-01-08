package com.skirpsi.api.posyandu.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.repository.UserRepository;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired UserRepository userRepo;
	@GetMapping("/getone")
	public ResponseEntity<UserPosyandu> testGet() {
		UserPosyandu x = userRepo.findById(1).get();
		try {
			return new ResponseEntity<>(x, HttpStatus.OK);	
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserPosyandu> getUserbyId(@PathVariable("id") Integer id){
		Optional<UserPosyandu> userData = userRepo.findById(id);
		
		if(userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	  @PostMapping()
	  public ResponseEntity<UserPosyandu> createUser(@RequestBody UserPosyandu user) {
	    try {
	    	
	    	UserPosyandu _user = new UserPosyandu();
	    	_user.setAlamatUser(user.getAlamatUser());
	    	_user.setNamaUser(user.getNamaUser());
	    	_user.setNoTeleponUser(user.getNoTeleponUser());
	    	_user.setPasswordUser(user.getPasswordUser());
	    	_user.setUserType(user.getUserType());
	    	userRepo.save(_user);
//	    	_user.setIdUser()
	      return new ResponseEntity<>(_user, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  
	  @PutMapping("/{id}")
	  public ResponseEntity<UserPosyandu> updateUser(@RequestBody UserPosyandu user,@PathVariable("id") Integer id) {
		  Optional<UserPosyandu>  userData = userRepo.findById(id);
		  
		 if(userData.isPresent()) {
			UserPosyandu _user = userData.get();
	    	_user.setAlamatUser(user.getAlamatUser());
	    	_user.setNamaUser(user.getNamaUser());
	    	_user.setNoTeleponUser(user.getNoTeleponUser());
	    	_user.setPasswordUser(user.getPasswordUser());
	    	_user.setUserType(user.getUserType());
	    	userRepo.save(_user);
	    	return new ResponseEntity<>(_user, HttpStatus.CREATED);
	    	
		 }else {
			 return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	  }
	  
	  @DeleteMapping("/{id}")
	  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") Integer id) {
		  Optional<UserPosyandu>  userData = userRepo.findById(id);
		  if(userData.isPresent()) {
		    	userRepo.deleteById(id);
		    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		  }else {
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }

	  }
	  
}
