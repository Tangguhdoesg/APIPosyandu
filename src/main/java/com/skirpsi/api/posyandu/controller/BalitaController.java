package com.skirpsi.api.posyandu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.entity.intfc.BalitaInterface;
import com.skirpsi.api.posyandu.service.BalitaService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("balita")
public class BalitaController {
	
	@Autowired BalitaService balitaSer;
	
	
	@GetMapping("/user/all")
	public ResponseEntity<List<Balita>> getAll(){
		List<Balita> all = balitaSer.getAll();
		
		if(all.size()>0) {
			return new ResponseEntity<>(all,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	@GetMapping("/user/{id}")
	public ResponseEntity<List<BalitaInterface>> getBalitaByIdOrtu(@PathVariable("id") Integer id){
		
		List<BalitaInterface> data = balitaSer.getByIdWithIdUser(id);
		
		if(data==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BalitaInterface> getBalitaById(@PathVariable("id") Integer id){
		BalitaInterface data = balitaSer.getBalitaInterfaceById(id);
		
		if(data==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}
	
	@GetMapping("/ortu")
	public ResponseEntity<List<Balita>> getBalitaByUser(@RequestBody UserPosyandu x){
		List<Balita> data = balitaSer.getByUser(x);
		
		return new ResponseEntity<>(data,HttpStatus.OK);
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
	public ResponseEntity<Map<String, Object>> updateBalita(@RequestBody Balita balita,@PathVariable("id") Integer id){
		Balita _balita = balitaSer.getById(id);
		
		System.out.println(balita.getNamaBalita());
		System.out.println(balita.getTempatLahirBalita());
		System.out.println(balita.getTinggiSaatLahirBalita());
		System.out.println(balita.getBeratSaatLahirBalita());
//		System.out.println(balita.getNikBalita());
		
		if(_balita==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			_balita.setBeratSaatLahirBalita(balita.getBeratSaatLahirBalita());
			_balita.setJenisKelaminBalita(balita.getJenisKelaminBalita());
			_balita.setNamaBalita(balita.getNamaBalita());
			_balita.setTanggalLahirBalita(balita.getTanggalLahirBalita());
			_balita.setTempatLahirBalita(balita.getTempatLahirBalita());
			_balita.setTinggiSaatLahirBalita(balita.getTinggiSaatLahirBalita());
			balitaSer.Insert(_balita);
			ObjectMapper oMapper = new ObjectMapper();
	    	@SuppressWarnings("unchecked")
			Map<String, Object> result = oMapper.convertValue(_balita, Map.class);
	    	result.remove("idUser");
			return new ResponseEntity<>(result,HttpStatus.OK);
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
	
	@GetMapping("/all")
	public ResponseEntity<List<BalitaInterface>> getAllWithoutUser(){
		List<BalitaInterface> data = balitaSer.getAllWithoutUser();
		
		return new ResponseEntity<>(data,HttpStatus.OK);
	}

}
