
package com.skirpsi.api.posyandu.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.Kegiatan;
import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.entity.intfc.KegiatanInterface;
import com.skirpsi.api.posyandu.entity.intfc.UserInterface;
import com.skirpsi.api.posyandu.misc.CreateKegiatan;
import com.skirpsi.api.posyandu.service.BalitaService;
import com.skirpsi.api.posyandu.service.KegiatanService;
import com.skirpsi.api.posyandu.service.UserPosyanduService;
import com.skirpsi.api.posyandu.service.WhatsappService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("kegiatan")
public class KegiatanController {
	
	@Autowired KegiatanService kegiatanSer;
	
	@Autowired WhatsappService whatServ;
	
	@Autowired UserPosyanduService userServ;
	
	@Autowired BalitaService balitaServ;
	
	@PostMapping()
	public ResponseEntity<Map<String, Object>> createKegiatan(@RequestBody CreateKegiatan kegiatan,@RequestParam("file") MultipartFile file){
		UserPosyandu user = userServ.getByNIKUser(kegiatan.getNikPenanggungjawab());
		
		System.out.println(kegiatan.getNamaKegiatan());
		System.out.println(kegiatan.getNikPenanggungjawab());
		System.out.println(file.getOriginalFilename());
		
		if(user==null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		Kegiatan newKegiatan = new Kegiatan();
		
//		Kegiatan  x = kegiatanSer.insert(newKegiatan);
		Kegiatan  x = newKegiatan;
		
//		if(x==null) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
		ObjectMapper oMapper = new ObjectMapper();
    	@SuppressWarnings("unchecked")
		Map<String, Object> result = oMapper.convertValue(x, Map.class);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateKegiatan(@RequestBody Kegiatan kegiatan,@PathVariable("id") Integer id){
		Kegiatan keg = kegiatanSer.getById(id);

		if(keg==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			keg.setTanggalKegiatan(kegiatan.getTanggalKegiatan());
			keg.setLokasiKegiatan(kegiatan.getLokasiKegiatan());
			keg.setNamaKegiatan(kegiatan.getNamaKegiatan());
			keg.setNamaPosterKegiatan(kegiatan.getNamaPosterKegiatan());
			keg.setPosterKegiatan(kegiatan.getPosterKegiatan());
			
			kegiatanSer.insert(keg);
			
			ObjectMapper oMapper = new ObjectMapper();
	    	@SuppressWarnings("unchecked")
			Map<String, Object> result = oMapper.convertValue(keg, Map.class);
	    	result.remove("penanggungJawabKegiatan");
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Kegiatan> deleteKegiatan(@PathVariable("id") Integer id){
		Kegiatan x = kegiatanSer.delete(id);
		
		if(x==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> findById(@PathVariable("id") Integer id){
		Kegiatan data = kegiatanSer.getById(id);
		if(data==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		UserPosyandu ortu = data.getPenanggungJawabKegiatan();
		ObjectMapper oMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> result = oMapper.convertValue(data, Map.class);
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date d = new Date(Long.parseLong(result.get("tanggalKegiatan").toString()));
		String date = simpleDateFormat.format(d);
		result.put("nikPetugas", ortu.getNikUser());
		result.put("namaPetugas", ortu.getNamaUser());
		result.remove("tanggalKegiatan");
		result.remove("penanggungJawabKegiatan");
		result.put("tanggalKegiatan", date);
		return new ResponseEntity<>(result,HttpStatus.OK);
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Map<String, Object>>> findAllWithoutUser(){
		List<KegiatanInterface> data = kegiatanSer.findALlWithoutUser();
		
		if(data.size()==0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
		
		ObjectMapper oMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> result = oMapper.convertValue(data, List.class);
		List<Map<String, Object>> res = new ArrayList<>();
		for (Map<String, Object> x : result) {
			UserPosyandu ortu = userServ.getOneById((Integer) x.get("idUser"));

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date d = new Date(Long.parseLong(x.get("tanggalKegiatan").toString()));
			String date = simpleDateFormat.format(d);
			x.put("nikPetugas", ortu.getNikUser());
			x.put("namaPetugas", ortu.getNamaUser());
			x.remove("tanggalKegiatan");
			x.remove("penanggungJawabKegiatan");
			x.put("tanggalKegiatan", date);
			res.add(x);
		}
		return new ResponseEntity<>(res,HttpStatus.OK);	
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<List<Map<String, Object>>> getKegiatan(@PathVariable("id") Integer id){
		List<KegiatanInterface> data = kegiatanSer.getKegiatanByUserId(id);
		
		if(data.size()==0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
		}
		
		ObjectMapper oMapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> result = oMapper.convertValue(data, List.class);
		List<Map<String, Object>> res = new ArrayList<>();
		for (Map<String, Object> x : result) {
			UserPosyandu ortu = userServ.getOneById((Integer) x.get("idUser"));

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date d = new Date(Long.parseLong(x.get("tanggalKegiatan").toString()));
			String date = simpleDateFormat.format(d);
			x.put("nikPetugas", ortu.getNikUser());
			x.put("namaPetugas", ortu.getNamaUser());
			x.remove("tanggalKegiatan");
			x.remove("penanggungJawabKegiatan");
			x.put("tanggalKegiatan", date);
			res.add(x);
		}
		return new ResponseEntity<>(res,HttpStatus.OK);	
		
	}
	

	@Scheduled(cron = "${cronExpresKegiatan}")
	@GetMapping("/reminder")
	public void sendReminderForKegiatan(){
		List<KegiatanInterface> res = kegiatanSer.getForReminderKegiatan();
		
		for (KegiatanInterface x : res) {
			  List<UserInterface> allUser = userServ.getAllOrangTua();
			  for (UserInterface y : allUser) {
				  whatServ.sendReminderKegiatan(y,x);
			  }
		}
	}
}
