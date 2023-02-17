package com.skirpsi.api.posyandu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.CheckUp;
import com.skirpsi.api.posyandu.entity.intfc.CheckupInterface;
import com.skirpsi.api.posyandu.repository.CheckupRepository;

@Service
public class CheckupService {
	@Autowired CheckupRepository checkupRepo;
	
	public List<CheckUp> getAll(){
		
		List<CheckUp> ret = checkupRepo.findAll();
		
		return ret;
	}
	
	public CheckUp getById(Integer id) {
		Optional<CheckUp> checkupData = checkupRepo.findById(id);
		
		if(checkupData.isPresent()) {
			return checkupData.get();
		}else {
			return null;
		}
	}
	
	public CheckUp insert(CheckUp data) {
		try {
			checkupRepo.save(data);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return null;
		}
		return data;
	}
	
	public CheckUp delete(Integer id) {
		CheckUp x = getById(id);
		
		if(x==null) {
			return null;
		}else {
			try {
				checkupRepo.delete(x);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
				return null;
			}
		}
		return x;
	}
	
	public List<CheckupInterface> getByIdBalita(Integer id){
		List<CheckupInterface> x = checkupRepo.findByIdBalita(id);
		
		return x;
	}
	
	public List<CheckupInterface> getAllWithoutBalita(){
		List<CheckupInterface> x = checkupRepo.findAllWithoutBalita();
		
		return x;
	}
}
