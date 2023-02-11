package com.skirpsi.api.posyandu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skirpsi.api.posyandu.entity.Kegiatan;
import com.skirpsi.api.posyandu.entity.intfc.KegiatanInterface;
import com.skirpsi.api.posyandu.repository.KegiatanRepository;

@Service
public class KegiatanService {
	
	@Autowired KegiatanRepository kegiatanRepo;
	
	public List<Kegiatan> getAll(){
		
		List<Kegiatan> ret = kegiatanRepo.findAll();
		
		return ret;
	}
	
	public Kegiatan getById(Integer id) {
		Optional<Kegiatan> kegiatanData = kegiatanRepo.findById(id);
		
		if(kegiatanData.isPresent()) {
			return kegiatanData.get();
		}else {
			return null;
		}
	}
	
	public Kegiatan insert(Kegiatan data) {
		try {
			kegiatanRepo.save(data);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return null;
		}
		
		return data;
	}
	
	public Kegiatan delete(Integer id) {
		Kegiatan x = getById(id);
		if(x==null) {
			return null;
		}else {
			try {
				kegiatanRepo.delete(x);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
				return null;
			}
		}
		return x;
	}
	
	public List<KegiatanInterface> findByIdUser(Integer id){
		
		List<KegiatanInterface> x = kegiatanRepo.findByIdUser(id);
		
		return x;
		
	}

}
