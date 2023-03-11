package com.skirpsi.api.posyandu.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
import com.skirpsi.api.posyandu.entity.Imunisasi;
import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.entity.intfc.ImunisasiInterface;
import com.skirpsi.api.posyandu.repository.ImunisasiRepository;
import com.skirpsi.api.posyandu.service.BalitaService;
import com.skirpsi.api.posyandu.service.ImunisasiService;
import com.skirpsi.api.posyandu.service.UserPosyanduService;
import com.skirpsi.api.posyandu.service.WhatsappService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("imunisasi")
public class ImunisasiController {
	
	@Autowired ImunisasiService imunisasiSer;
	
	@Autowired BalitaService balitaServ;
	
	@Autowired WhatsappService whatServ;
	
	@Autowired UserPosyanduService userServ;
	
//	@GetMapping("/balita/all")
//	public ResponseEntity<List<Imunisasi>> getAll(){
//		List<Imunisasi> all = imunisasiSer.getAll();
//		
//		if(all.size()>0) {
//			return new ResponseEntity<>(all,HttpStatus.OK);
//		}else {
//			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
//		}
//	}
//	
//	@GetMapping("/balita/{id}")
//	public ResponseEntity<Imunisasi> getImunisasiById(@PathVariable("id") Integer id){
//		Imunisasi data = imunisasiSer.getById(id);
//		
//		if(data==null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}else {
//			return new ResponseEntity<>(data,HttpStatus.OK);
//		}
//	}
//	
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
	
	@GetMapping("/balita/{id}")
	public ResponseEntity<List<Map<String, Object>>> getImunisasiByIdBalita(@PathVariable("id") Integer id){
		List<Imunisasi> data = imunisasiSer.getByIdBalita(id);
		if(data==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			ObjectMapper oMapper = new ObjectMapper();
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> result = oMapper.convertValue(data, List.class);
			List<Map<String, Object>> res = new ArrayList<>();
			Integer count = 0;
			for (Map<String, Object> x : result) {
				Imunisasi y = data.get(count);
				Balita z = y.getIdBalita();
				String pattern = "yyyy-MM-dd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				Date d1 = new Date(Long.parseLong(x.get("tanggalImunisasi").toString()));
				Date d2 = new Date(Long.parseLong(x.get("tanggalImunisasiBerikutnya").toString()));
				String date1 = simpleDateFormat.format(d1);
				String date2 = simpleDateFormat.format(d2);
				x.remove("tanggalImunisasi");
				x.remove("tanggalImunisasiBerikutnya");
				x.put("tanggalImunisasi", date1);
				x.put("tanggalImunisasiBerikutnya", date2);
				x.put("idBalita",z.getIdBalita());
				x.put("namaBalita",z.getNamaBalita());
				x.put("namaOrangTua",z.getIdUser().getNamaUser());
				res.add(x);
				count++;

			}
			return new ResponseEntity<>(res,HttpStatus.OK);
		}
	}
//	
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getImunisasiByBalitaId(@PathVariable("id") Integer id){		
		Imunisasi data = imunisasiSer.getById(id);
		
		if(data==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			Balita x = data.getIdBalita();
			ObjectMapper oMapper = new ObjectMapper();
			@SuppressWarnings("unchecked")
			Map<String, Object> result = oMapper.convertValue(data, Map.class);
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date d1 = new Date(Long.parseLong(result.get("tanggalImunisasi").toString()));
			Date d2 = new Date(Long.parseLong(result.get("tanggalImunisasiBerikutnya").toString()));
			String date1 = simpleDateFormat.format(d1);
			String date2 = simpleDateFormat.format(d2);
			result.remove("tanggalImunisasi");
			result.remove("tanggalImunisasiBerikutnya");
			result.put("tanggalImunisasi", date1);
			result.put("tanggalImunisasiBerikutnya", date2);
			result.put("idBalita",x.getIdBalita());
			result.put("namaBalita",x.getNamaBalita());
			result.put("namaOrangTua",x.getIdUser().getNamaUser());
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Map<String, Object>>> getAllWithoutBalita(){		
		List<Imunisasi> data = imunisasiSer.getAll();
		if(data==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			ObjectMapper oMapper = new ObjectMapper();
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> result = oMapper.convertValue(data, List.class);
			List<Map<String, Object>> res = new ArrayList<>();
			Integer count = 0;
			for (Map<String, Object> x : result) {
				Imunisasi y = data.get(count);
				Balita z = y.getIdBalita();
				String pattern = "yyyy-MM-dd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				System.out.println(x.get("tanggalImunisasi"));
				Date d1 = new Date(Long.parseLong(x.get("tanggalImunisasi").toString()));
				Date d2 = new Date(Long.parseLong(x.get("tanggalImunisasiBerikutnya").toString()));
				String date1 = simpleDateFormat.format(d1);
				String date2 = simpleDateFormat.format(d2);
				x.remove("tanggalImunisasi");
				x.remove("tanggalImunisasiBerikutnya");
				x.put("tanggalImunisasi", date1);
				x.put("tanggalImunisasiBerikutnya", date2);
				x.put("idBalita",z.getIdBalita());
				x.put("namaBalita",z.getNamaBalita());
				x.put("namaOrangTua",z.getIdUser().getNamaUser());
				res.add(x);
				count++;

			}
			return new ResponseEntity<>(res,HttpStatus.OK);
		}
		
		
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
