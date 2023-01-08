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

import com.skirpsi.api.posyandu.entity.Kegiatan;
import com.skirpsi.api.posyandu.repository.KegiatanRepository;

@RestController
@RequestMapping("kegiatan")
public class KegiatanController {
	
	@Autowired KegiatanRepository kegiatanRepo;
	
	@GetMapping("/getone")
	public ResponseEntity<Kegiatan> testGet() {
		Kegiatan x = kegiatanRepo.findById(1).get();
		
		try {
			return new ResponseEntity<>(x, HttpStatus.OK);	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Kegiatan> getKegiatan(@PathVariable("id") Integer id){
		Optional<Kegiatan> kegiatanData = kegiatanRepo.findById(id);
		
		if(kegiatanData.isPresent()) {
			return new ResponseEntity<>(kegiatanData.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping()
	public ResponseEntity<Kegiatan> createKegiatan(@RequestBody Kegiatan kegiatan){
		try {
			Kegiatan _kegiatan  = new Kegiatan();
			_kegiatan.setAnggalKegiatan(kegiatan.getAnggalKegiatan());
//			_kegiatan.setIdKegiatan(null);
			_kegiatan.setIdPenanggungJawab(kegiatan.getIdPenanggungJawab());
			_kegiatan.setLokasiKegiatan(kegiatan.getLokasiKegiatan());
			_kegiatan.setNamaKegiatan(kegiatan.getNamaKegiatan());
			kegiatanRepo.save(_kegiatan);
			return new ResponseEntity<>(_kegiatan,HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Kegiatan> updateKegiatan(@RequestBody Kegiatan kegiatan,@PathVariable("id") Integer id){
		Optional<Kegiatan> kegiatanData = kegiatanRepo.findById(id);
		if(kegiatanData.isPresent()) {
			try {
				Kegiatan _kegiatan  = kegiatanData.get();
				_kegiatan.setAnggalKegiatan(kegiatan.getAnggalKegiatan());
//				_kegiatan.setIdKegiatan(null);
				_kegiatan.setIdPenanggungJawab(kegiatan.getIdPenanggungJawab());
				_kegiatan.setLokasiKegiatan(kegiatan.getLokasiKegiatan());
				_kegiatan.setNamaKegiatan(kegiatan.getNamaKegiatan());
				kegiatanRepo.save(_kegiatan);
				return new ResponseEntity<>(_kegiatan,HttpStatus.CREATED);
			} catch (Exception e) {
				// TODO: handle exception
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Kegiatan> deleteKegiatan(@PathVariable("id") Integer id){
		Optional<Kegiatan> kegiatanData = kegiatanRepo.findById(id);
		
		if(kegiatanData.isPresent()) {
			kegiatanRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
