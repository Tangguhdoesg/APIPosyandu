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

import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.repository.BalitaRepository;

@RestController
@RequestMapping("balita")
public class BalitaController {
	
	@Autowired BalitaRepository balitaRepo;
	
	@GetMapping("/getone")
	public ResponseEntity<Balita> testGet(){
		Balita x = balitaRepo.findById(1).get();
		
		return new ResponseEntity<>(x,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Balita> getUserbyId(@PathVariable("id") Integer id){
		Optional<Balita> userData = balitaRepo.findById(id);
		
		if(userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping()
	public ResponseEntity<Balita> createKegiatan(@RequestBody Balita balita){
		try {
//			Kegiatan _kegiatan  = new Kegiatan();
			Balita _balita = new Balita();
			_balita.setBeratSaatLahir(balita.getBeratSaatLahir());
			_balita.setJenisKelaminBalita(balita.getJenisKelaminBalita());
			_balita.setNamabalita(balita.getNamabalita());
			_balita.setTanggalLahirBalita(balita.getTanggalLahirBalita());
			_balita.setTempatLahirBalita(balita.getTempatLahirBalita());
			_balita.setTinggiSaatLahir(balita.getTinggiSaatLahir());
			_balita.setUser(balita.getUser());
			balitaRepo.save(_balita);
			return new ResponseEntity<>(_balita,HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Balita> updateKegiatan(@RequestBody Balita balita,@PathVariable("id") Integer id){
		Optional<Balita> balitaData = balitaRepo.findById(id);
		if(balitaData.isPresent()) {
			try {
//				Kegiatan _kegiatan  = new Kegiatan();
				Balita _balita = balitaData.get();
				_balita.setBeratSaatLahir(balita.getBeratSaatLahir());
				_balita.setJenisKelaminBalita(balita.getJenisKelaminBalita());
				_balita.setNamabalita(balita.getNamabalita());
				_balita.setTanggalLahirBalita(balita.getTanggalLahirBalita());
				_balita.setTempatLahirBalita(balita.getTempatLahirBalita());
				_balita.setTinggiSaatLahir(balita.getTinggiSaatLahir());
				_balita.setUser(balita.getUser());
				balitaRepo.save(_balita);
				return new ResponseEntity<>(_balita,HttpStatus.CREATED);
			} catch (Exception e) {
				// TODO: handle exception
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Balita> deleteBalita(@PathVariable("id") Integer id){
		Optional<Balita> kegiatanData = balitaRepo.findById(id);
		
		if(kegiatanData.isPresent()) {
			balitaRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	

}
