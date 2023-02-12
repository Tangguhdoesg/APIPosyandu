package com.skirpsi.api.posyandu.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
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
import com.skirpsi.api.posyandu.entity.intfc.KegiatanInterface;
import com.skirpsi.api.posyandu.service.KegiatanService;

@RestController
@RequestMapping("kegiatan")
public class KegiatanController {
	
	@Autowired KegiatanService kegiatanSer;
	
	@GetMapping("/user/all")
	public ResponseEntity<List<Kegiatan>> testGet(){
		List<Kegiatan> ret = kegiatanSer.getAll();
		
		if(ret.size()>0) {
			return new ResponseEntity<>(ret,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<Kegiatan> getKegiatan(@PathVariable("id") Integer id){
		Kegiatan ret = kegiatanSer.getById(id);
		
		if(ret==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(ret,HttpStatus.OK);
		}
		
	}
	
	@PostMapping()
	public ResponseEntity<Kegiatan> createKegiatan(@RequestBody Kegiatan kegiatan){
		Kegiatan ret = kegiatanSer.insert(kegiatan);
		
		if(ret==null) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			return new ResponseEntity<>(ret,HttpStatus.OK);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Kegiatan> updateKegiatan(@RequestBody Kegiatan kegiatan,@PathVariable("id") Integer id){
		Kegiatan keg = kegiatanSer.getById(id);
		
		if(keg==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			keg.setAnggalKegiatan(kegiatan.getAnggalKegiatan());
			keg.setIdPenanggungJawab(kegiatan.getIdPenanggungJawab());
			keg.setLokasiKegiatan(kegiatan.getLokasiKegiatan());
			keg.setNamaKegiatan(kegiatan.getNamaKegiatan());
			keg.setNamaPoster(kegiatan.getNamaPoster());
			keg.setPosterKegiatan(kegiatan.getPosterKegiatan());
			
			kegiatanSer.insert(keg);
			
			return new ResponseEntity<>(keg,HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Kegiatan> deleteKegiatan(@PathVariable("id") Integer id){
//		Optional<Kegiatan> kegiatanData = kegiatanRepo.findById(id);
		Kegiatan x = kegiatanSer.delete(id);
		
		if(x==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<KegiatanInterface> findByIdUser(@PathVariable("id") Integer id){
		KegiatanInterface data = kegiatanSer.findByIdUser(id);
		
		return new ResponseEntity<KegiatanInterface>(data,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<KegiatanInterface>> findAllWithoutUser(){
		List<KegiatanInterface> data = kegiatanSer.findALlWithoutUser();
		
		return new ResponseEntity<>(data,HttpStatus.OK);
	}

}
