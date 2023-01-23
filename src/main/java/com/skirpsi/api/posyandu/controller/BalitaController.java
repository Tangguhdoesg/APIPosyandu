package com.skirpsi.api.posyandu.controller;

import java.util.List;

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
import com.skirpsi.api.posyandu.service.BalitaService;

@RestController
@RequestMapping("balita")
public class BalitaController {
	
	@Autowired BalitaService balitaSer;
	
	
	@GetMapping("/all")
	public ResponseEntity<List<Balita>> getAll(){
		List<Balita> all = balitaSer.getAll();
		
		if(all.size()>0) {
			return new ResponseEntity<>(all,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	@GetMapping("/{id}")
	public ResponseEntity<Balita> getUserbyId(@PathVariable("id") Integer id){
		
		Balita data = balitaSer.getById(id);
		
		if(data==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}
	
	@PostMapping()
	public ResponseEntity<Balita> createBalita(@RequestBody Balita balita){
		Balita x = balitaSer.Insert(balita);
		
		if(x==null) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			return new ResponseEntity<>(x,HttpStatus.OK);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Balita> updateBalita(@RequestBody Balita balita,@PathVariable("id") Integer id){
		Balita _balita = balitaSer.getById(id);
		
		if(_balita==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			_balita.setBeratSaatLahir(balita.getBeratSaatLahir());
			_balita.setJenisKelaminBalita(balita.getJenisKelaminBalita());
			_balita.setNamabalita(balita.getNamabalita());
			_balita.setTanggalLahirBalita(balita.getTanggalLahirBalita());
			_balita.setTempatLahirBalita(balita.getTempatLahirBalita());
			_balita.setTinggiSaatLahir(balita.getTinggiSaatLahir());
			_balita.setUser(balita.getUser());
			balitaSer.Insert(_balita);
			
			return new ResponseEntity<>(_balita,HttpStatus.OK);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Balita> deleteBalita(@PathVariable("id") Integer id){
		Balita x = balitaSer.delete(id);
		
		if(x==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	

}
