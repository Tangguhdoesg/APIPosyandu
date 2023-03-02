package com.skirpsi.api.posyandu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.Imunisasi;
import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.entity.intfc.ImunisasiInterface;
import com.skirpsi.api.posyandu.repository.ImunisasiRepository;
import com.skirpsi.api.posyandu.service.BalitaService;
import com.skirpsi.api.posyandu.service.ImunisasiService;
import com.skirpsi.api.posyandu.service.UserPosyanduService;
import com.skirpsi.api.posyandu.service.WhatsappService;

@RestController
@RequestMapping("imunisasi")
public class ImunisasiController {
	
	@Autowired ImunisasiService imunisasiSer;
	
	@Autowired BalitaService balitaServ;
	
	@Autowired WhatsappService whatServ;
	
	@Autowired UserPosyanduService userServ;
	
	@GetMapping("/balita/all")
	public ResponseEntity<List<Imunisasi>> getAll(){
		List<Imunisasi> all = imunisasiSer.getAll();
		
		if(all.size()>0) {
			return new ResponseEntity<>(all,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@GetMapping("/balita/{id}")
	public ResponseEntity<Imunisasi> getImunisasiById(@PathVariable("id") Integer id){
		Imunisasi data = imunisasiSer.getById(id);
		
		if(data==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}
	
	@PostMapping()
	public ResponseEntity<Imunisasi> createImunisasi(@RequestBody Imunisasi imunisasi){
		Imunisasi x = imunisasiSer.insert(imunisasi);
		
		if(x==null) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}else {
			return new ResponseEntity<>(x,HttpStatus.OK);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Imunisasi> updateImunisasi(@RequestBody Imunisasi imunisasi,@PathVariable("id") Integer id){
		Imunisasi _imunisasi = imunisasiSer.getById(id);
		
		if(_imunisasi==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			_imunisasi.setIdBalita(imunisasi.getIdBalita());
			_imunisasi.setNamaImunisasi(imunisasi.getNamaImunisasi());
			_imunisasi.setTanggalImunisasi(imunisasi.getTanggalImunisasi());
			_imunisasi.setCatatan(imunisasi.getCatatan());
			imunisasiSer.insert(_imunisasi);
			
			return new ResponseEntity<>(_imunisasi,HttpStatus.OK);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Imunisasi> deleteImunisasi(@PathVariable("id") Integer id) {
		Imunisasi x = imunisasiSer.delete(id);
		
		if(x==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	
	@GetMapping("/balita")
	public ResponseEntity<List<Imunisasi>> getImunisasiByBalita(@RequestBody Balita b){
		List<Imunisasi> data = imunisasiSer.getByBalita(b);
		
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<List<ImunisasiInterface>> getImunisasiByBalitaId(@PathVariable("id") Integer id){
		List<ImunisasiInterface> data = imunisasiSer.getByIdBalita(id);
		
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<ImunisasiInterface>> getAllWithoutBalita(){
		List<ImunisasiInterface> data = imunisasiSer.getAllWithoutBalita();
		
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
	
	@Scheduled(cron = "${cronExpresImunisasi}")
	@GetMapping("/reminder")
	public void sendReminderForImunisasi(){
		
		List<ImunisasiInterface> res = imunisasiSer.getForReminderImunisasi();
		
		for(ImunisasiInterface x : res) {
			  Balita balita = balitaServ.getById(x.getIdBalita());
			  UserPosyandu user = balita.getIdUser();
			  
			  whatServ.sendReminderImunisasi(user, balita);
		}
		
	}
  
  
}
