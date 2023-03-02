package com.skirpsi.api.posyandu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.Imunisasi;
import com.skirpsi.api.posyandu.entity.intfc.ImunisasiInterface;
import com.skirpsi.api.posyandu.repository.ImunisasiRepository;

@Service
public class ImunisasiService {

	@Autowired ImunisasiRepository imunisasiRepo;
	
	public List<Imunisasi> getAll(){
		List<Imunisasi> ret = imunisasiRepo.findAll();
		
		return ret;
	}
	
	public Imunisasi getById(Integer id) {
		Optional<Imunisasi> imunisasiData = imunisasiRepo.findById(id);
		
		if(imunisasiData.isPresent()) {
			return imunisasiData.get();
		}else {
			return null;
		}		
	}
	
	public Imunisasi insert(Imunisasi data) {
		try {
			imunisasiRepo.save(data);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return null;
		}
		return data;
	}
	
	public Imunisasi delete(Integer id) {
		Imunisasi x = getById(id);
		
		if(x==null) {
			return null;
		}else {
			try {
				imunisasiRepo.delete(x);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
				return null;
			}
		}
		return x;
	}
	
	public List<Imunisasi> getByBalita (Balita b){
		List<Imunisasi> x = imunisasiRepo.findAllImunisasiByBalita(b);
		
		return x;
	}
	
	public List<ImunisasiInterface> getAllWithoutBalita(){
		List<ImunisasiInterface> x = imunisasiRepo.findAllWithourBalita();
		
		return x;
	}
	
	public List<ImunisasiInterface> getByIdBalita(Integer id) {
		List<ImunisasiInterface> x = imunisasiRepo.findByIdBalitaWithoutBalitaObj(id);
		
		return x;
	}
	
	public List<ImunisasiInterface> getForReminderImunisasi(){
		List<ImunisasiInterface> x = imunisasiRepo.getDataForReminderImunisasi();
		
		return x;
	}
}
