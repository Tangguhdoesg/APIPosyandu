package com.skirpsi.api.posyandu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.repository.UserRepository;

@RestController
@RequestMapping("api")
public class UserController {
	
	@Autowired UserRepository userRepo;
	@GetMapping("/getone")
	public String testGet() {
		UserPosyandu x = userRepo.findById(1).get();
		
		return x.getKegiatan().get(0).getNamaKegiatan();
		
	}

}
