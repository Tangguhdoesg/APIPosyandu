package com.skirpsi.api.posyandu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.entity.intfc.UserInterface;
import com.skirpsi.api.posyandu.repository.UserPosyanduRepository;

@Service
public class UserPosyanduService {
	
	@Autowired UserPosyanduRepository userRepo;
	
	public List<UserPosyandu> getAll(){
		List<UserPosyandu> ret = userRepo.findAll();
		
		return ret;
	}
	
	public UserPosyandu getOneById(Integer id) {
		Optional<UserPosyandu> userData = userRepo.findById(id);
		
		if(userData.isPresent()) {
			return userData.get();
		}else {
			return null;
		}
		
	}
	
	public UserPosyandu insert(UserPosyandu data) {
		try {
			userRepo.save(data);	
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
		return data;
	}
	
	public UserPosyandu delete(Integer data) {
		UserPosyandu x = getOneById(data);
		if(x==null) {
			return null;
		}else {
			try {
				userRepo.delete(x);
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
			return x;
		}
		
	}
	
	public UserInterface getOneByIdWithoutPassword(Integer id) {
		UserInterface x = userRepo.findOneWithId(id);
		
		return x;
	}
	
	public List<UserInterface> getAllWithoutPassword(){
		List<UserInterface> x = userRepo.findAllWithourPassword();
		
		return x;
	}
	
	public Boolean checkIfExistByPhone(String noTelp) {
		Boolean ret = userRepo.existsByNoTeleponUser(noTelp);
		
		return ret;
	}
	
	public List<UserInterface> getAllOrangTua(){
		List<UserInterface> x = userRepo.getAllOrangTua();
		
		return x;
	}
	
	public UserPosyandu getByNIKUser(String nik) {
		Optional<UserPosyandu> userData = userRepo.findByNikUser(nik);
		
		if(userData.isPresent()) {
			return userData.get();
		}else {
			return null;
		}
	}
}
