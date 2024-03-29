package com.skirpsi.api.posyandu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.entity.intfc.BalitaInterface;
import com.skirpsi.api.posyandu.repository.BalitaRepository;

@Service
public class BalitaService {

	@Autowired BalitaRepository balitaRepo;
	
	
	public List<Balita> getAll(){
		List<Balita> ret=balitaRepo.findAll();
		
		return ret;
	}
	
	public Balita getById(Integer id) {
		Optional<Balita> balitaData = balitaRepo.findById(id);
		
		if(balitaData.isPresent()) {
			return balitaData.get();
		}else {
			return null;
		}
	}
	
	public List<Balita> getByUser(UserPosyandu o) {
		List<Balita> x = balitaRepo.findAllBalitaByUser(o);
		
		return x;
	}
	
	public Balita Insert(Balita data) {
		try {
			balitaRepo.save(data);
		} catch (Exception e) {
			return null;
		}
		return data;
	}
	
	public Balita delete(Integer id) {
		Balita x = getById(id);
		
		if(x==null){
			return null;
		}else {
			try {
				balitaRepo.delete(x);
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
		}
		return x;
	}
	
	public List<BalitaInterface> getByIdWithIdUser(Integer x){
		List<BalitaInterface> y = balitaRepo.findByIdWithIdUser(x);
		
		return y;
	}
	
	public List<BalitaInterface> getAllWithoutUser(){
		List<BalitaInterface> y = balitaRepo.findAllWithoutUser();
		
		return y;
	}
	
	public BalitaInterface getBalitaInterfaceById(Integer id) {
		BalitaInterface ret = balitaRepo.findByIdBalitaInterface(id);
		
		return ret;
	}
	
	public Balita getBalitaByNIK(String nik) {
		Optional<Balita> data = balitaRepo.findByNikBalita(nik);
		
		if(data.isPresent()) {
			return data.get();
		}else {
			return null;
		}
	}
}
