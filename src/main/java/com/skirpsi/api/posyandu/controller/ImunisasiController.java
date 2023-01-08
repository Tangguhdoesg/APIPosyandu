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

import com.skirpsi.api.posyandu.entity.Imunisasi;
//import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.repository.ImunisasiRepository;

@RestController
@RequestMapping("imunisasi")
public class ImunisasiController {
	
	@Autowired ImunisasiRepository imunisasiRepo;
	
	@GetMapping("/getone")
	public ResponseEntity<Imunisasi> testGet(){
		Imunisasi x = imunisasiRepo.findById(1).get();
		try {
			return new ResponseEntity<>(x, HttpStatus.OK);	
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Imunisasi> getImunisasiById(@PathVariable("id") Integer id){
		Optional<Imunisasi> imunisasiData = imunisasiRepo.findById(1);
		
		if(imunisasiData.isPresent()) {
			return new ResponseEntity<>(imunisasiData.get(), HttpStatus.OK);	
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping()
	public ResponseEntity<Imunisasi> createImunisasi(@RequestBody Imunisasi imunisasi){
		try {
			Imunisasi _imunisasi = new Imunisasi();
			_imunisasi.setBalita(imunisasi.getBalita());
//			_imunisasi.setIdImunisasi(null);
			_imunisasi.setNamaImunisasi(imunisasi.getNamaImunisasi());
			_imunisasi.setTanggalImunisasi(imunisasi.getTanggalImunisasi());
//			_imunisasi.set
			imunisasiRepo.save(_imunisasi);
			return new ResponseEntity<>(_imunisasi, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Imunisasi> updateImunisasi(@RequestBody Imunisasi imunisasi,@PathVariable("id") Integer id){
		Optional<Imunisasi> imunisasiData = imunisasiRepo.findById(id);
		
		if(imunisasiData.isPresent()) {
			try {
				Imunisasi _imunisasi = imunisasiData.get();
				_imunisasi.setBalita(imunisasi.getBalita());
//				_imunisasi.setIdImunisasi(null);
				_imunisasi.setNamaImunisasi(imunisasi.getNamaImunisasi());
				_imunisasi.setTanggalImunisasi(imunisasi.getTanggalImunisasi());
//				_imunisasi.set
				imunisasiRepo.save(_imunisasi);
				return new ResponseEntity<>(_imunisasi, HttpStatus.CREATED);
			} catch (Exception e) {
				// TODO: handle exception
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteImunisasi(@PathVariable("id") Integer id) {
		Optional<Imunisasi>  imunisasiData = imunisasiRepo.findById(id);
		if(imunisasiData.isPresent()) {
			imunisasiRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
  
  
}
