package com.skirpsi.api.posyandu.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.entity.intfc.BalitaInterface;
import com.skirpsi.api.posyandu.misc.CreateBalitaEntity;
import com.skirpsi.api.posyandu.service.BalitaService;
import com.skirpsi.api.posyandu.service.UserPosyanduService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("balita")
public class BalitaController {
	
	@Autowired BalitaService balitaSer;
	
	@Autowired UserPosyanduService userSer;
	
	@GetMapping("/all")
	public ResponseEntity<List<Map<String, Object>>> getAllWithoutUser(){
		List<BalitaInterface> data = balitaSer.getAllWithoutUser();
		
		
		if(data == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}else {
			ObjectMapper oMapper = new ObjectMapper();
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> result = oMapper.convertValue(data, List.class);
			List<Map<String, Object>> res = new ArrayList<>();
			for (Map<String, Object> x : result) {
				UserPosyandu ortu = userSer.getOneById((Integer) x.get("idOrangTua"));
				
				String pattern = "yyyy-MM-dd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				Date d = new Date(Long.parseLong( x.get("tanggalLahirBalita").toString()));
				String date = simpleDateFormat.format(d);
				x.put("namaOrangTua", ortu.getNamaUser());
//				 "nikOrangTua" : "12341245",
				x.put("nikOrangTua", ortu.getNikUser());
				x.remove("tanggalLahirBalita");
				x.put("tanggalLahirBalita", date);
				res.add(x);
			}
			return new ResponseEntity<>(res,HttpStatus.OK);	
		}
		
		
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<List<Map<String, Object>>> getBalitaByIdOrtu(@PathVariable("id") Integer id){
		
		List<BalitaInterface> data = balitaSer.getByIdWithIdUser(id);
		
		
		
		if(data==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			ObjectMapper oMapper = new ObjectMapper();
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> result = oMapper.convertValue(data, List.class);
			List<Map<String, Object>> res = new ArrayList<>();
			for (Map<String, Object> x : result) {
				UserPosyandu ortu = userSer.getOneById((Integer) x.get("idOrangTua"));
				String pattern = "yyyy-MM-dd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				Date d = new Date(Long.parseLong( x.get("tanggalLahirBalita").toString()));
				String date = simpleDateFormat.format(d);
				x.put("namaOrangTua", ortu.getNamaUser());
				x.put("nikOrangTua", ortu.getNikUser());
				x.remove("tanggalLahirBalita");
				x.put("tanggalLahirBalita", date);
				res.add(x);
			}
			return new ResponseEntity<>(res,HttpStatus.OK);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getBalitaById(@PathVariable("id") Integer id){
		BalitaInterface data = balitaSer.getBalitaInterfaceById(id);

		if(data==null) {			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			UserPosyandu ortu = userSer.getOneById(data.getIdOrangTua());
			ObjectMapper oMapper = new ObjectMapper();
			@SuppressWarnings("unchecked")
			Map<String, Object> result = oMapper.convertValue(data, Map.class);
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date d = new Date(Long.parseLong(result.get("tanggalLahirBalita").toString()));
			String date = simpleDateFormat.format(d);
			result.put("namaOrangTua", ortu.getNamaUser());
			result.put("nikOrangTua", ortu.getNikUser());
			result.remove("tanggalLahirBalita");
			result.put("tanggalLahirBalita", date);
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
	}
	
	@PostMapping()
	public ResponseEntity<Map<String, Object>> createBalita(@RequestBody CreateBalitaEntity balita){
		
		UserPosyandu user = userSer.getByNIKUser(balita.getNikOrangTua());
		if(user==null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}else {
			Balita newBalita = new Balita();
			newBalita.setBeratSaatLahirBalita(balita.getBeratSaatLahirBalita());
			newBalita.setTinggiSaatLahirBalita(balita.getTinggiSaatLahirBalita());
			newBalita.setJenisKelaminBalita(balita.getJenisKelaminBalita());
			newBalita.setTanggalLahirBalita(balita.getTanggalLahirBalita());
			newBalita.setTempatLahirBalita(balita.getTempatLahirBalita());
			newBalita.setNikBalita(balita.getNikBalita());
			newBalita.setNamaBalita(balita.getNamaBalita());
			newBalita.setIdUser(user);
			
			Balita x = balitaSer.Insert(newBalita);
			
			if(x==null) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}else {
				balita.setNamaOrangTua(user.getNamaUser());
				balita.setIdbalita(x.getIdBalita());
				ObjectMapper oMapper = new ObjectMapper();
		    	@SuppressWarnings("unchecked")
				Map<String, Object> result = oMapper.convertValue(balita, Map.class);
		    	String pattern = "yyyy-MM-dd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				Date d = new Date(Long.parseLong(result.get("tanggalLahirBalita").toString()));
				String date = simpleDateFormat.format(d);
				result.put("namaOrangTua", user.getNamaUser());
				result.put("nikOrangTua", user.getNikUser());
		    	result.remove("tanggalLahirBalita");
		    	result.put("tanggalLahirBalita", date);
				return new ResponseEntity<>(result,HttpStatus.OK);
			}
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateBalita(@RequestBody CreateBalitaEntity balita,@PathVariable("id") Integer id){
		Balita _balita = balitaSer.getById(id);

		if(_balita==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			_balita.setBeratSaatLahirBalita(balita.getBeratSaatLahirBalita());
			_balita.setJenisKelaminBalita(balita.getJenisKelaminBalita());
			_balita.setNamaBalita(balita.getNamaBalita());
			_balita.setTempatLahirBalita(balita.getTempatLahirBalita());
			_balita.setTinggiSaatLahirBalita(balita.getTinggiSaatLahirBalita());
			
			balitaSer.Insert(_balita);
			UserPosyandu ortu = userSer.getOneById(_balita.getIdUser().getIdUser());
			ObjectMapper oMapper = new ObjectMapper();
	    	@SuppressWarnings("unchecked")
			Map<String, Object> result = oMapper.convertValue(_balita, Map.class);
	    	String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date d = new Date(Long.parseLong(result.get("tanggalLahirBalita").toString()));
			String date = simpleDateFormat.format(d);
			result.put("nikOrangTua", ortu.getNikUser());
	    	result.remove("idUser");
	    	result.remove("tanggalLahirBalita");
	    	result.put("tanggalLahirBalita", date);
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

}
