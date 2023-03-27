package com.skirpsi.api.posyandu.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
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
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.CheckUp;
import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.entity.intfc.BalitaInterface;
import com.skirpsi.api.posyandu.entity.intfc.CheckupInterface;
import com.skirpsi.api.posyandu.entity.intfc.UserInterface;
import com.skirpsi.api.posyandu.misc.CreateCheckupEntity;
import com.skirpsi.api.posyandu.service.BalitaService;
import com.skirpsi.api.posyandu.service.CheckupService;
import com.skirpsi.api.posyandu.service.ReportService;
import com.skirpsi.api.posyandu.service.UserPosyanduService;
import com.skirpsi.api.posyandu.service.WhatsappService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("checkup")
public class CheckupController {
	
	@Autowired CheckupService checkupSer;
	
	@Autowired WhatsappService whatServ;
	
	@Autowired UserPosyanduService userServ;
	
	@Autowired BalitaService balitaServ;
	
	@PostMapping()
	public ResponseEntity<Map<String, Object>> createCheckup(@RequestBody CreateCheckupEntity checkup){
			CheckUp data = new CheckUp();
			Balita balita = balitaServ.getBalitaByNIK(checkup.getNikBalita());
			if(balita==null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			data.setBeratBadan(checkup.getBeratBadan());
			data.setCatatan(checkup.getCatatan());
			data.setIdBalita(balita);
			data.setLingkarKepala(checkup.getLingkarKepala());
			data.setLingkarLengan(checkup.getLingkarLengan());
			data.setTanggalCheckup(checkup.getTanggalCheckup());
			data.setTanggalCheckupBerikutnya(checkup.getTanggalCheckupBerikutnya());
			data.setTinggiBadan(checkup.getTinggiBadan());
			CheckUp newCheckup = checkupSer.insert(data);
			
			if(newCheckup==null) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}else{
				ObjectMapper oMapper = new ObjectMapper();
				@SuppressWarnings("unchecked")
				Map<String, Object> result = oMapper.convertValue(data, Map.class);
				String pattern = "yyyy-MM-dd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				Date d1 = new Date(Long.parseLong(result.get("tanggalCheckup").toString()));
				Date d2 = new Date(Long.parseLong(result.get("tanggalCheckupBerikutnya").toString()));
				String date1 = simpleDateFormat.format(d1);
				String date2 = simpleDateFormat.format(d2);
				Date lahirBalita = balita.getTanggalLahirBalita();
				Date tanggalCheckup = data.getTanggalCheckup();
				result.remove("idBalita");
				result.remove("tanggalCheckup");
				result.remove("tanggalCheckupBerikutnya");
				result.put("tanggalCheckup", date1);
				result.put("tanggalCheckupBerikutnya", date2);
				result.put("idBalita",balita.getIdBalita());
				result.put("umurBalita",getMonthsDifference(lahirBalita, tanggalCheckup));
				result.put("namaBalita",balita.getNamaBalita());
				result.put("nikBalita",balita.getNikBalita());
				
				return new ResponseEntity<>(result,HttpStatus.OK);
			}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateCheckup(@RequestBody CreateCheckupEntity checkup,@PathVariable("id") Integer id){
		CheckUp _checkup = checkupSer.getById(id);
		Balita balita = balitaServ.getBalitaByNIK(checkup.getNikBalita());
		
		if(_checkup==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			_checkup.setBeratBadan(checkup.getBeratBadan());
			_checkup.setLingkarKepala(checkup.getLingkarKepala());
			_checkup.setLingkarLengan(checkup.getLingkarLengan());
			_checkup.setTanggalCheckup(checkup.getTanggalCheckup());
			_checkup.setTanggalCheckupBerikutnya(checkup.getTanggalCheckupBerikutnya());
			_checkup.setTinggiBadan(checkup.getTinggiBadan());
			_checkup.setCatatan(checkup.getCatatan());
			_checkup.setIdBalita(balita);
			
			checkupSer.insert(_checkup);
			
			ObjectMapper oMapper = new ObjectMapper();
			Balita z = _checkup.getIdBalita();
			@SuppressWarnings("unchecked")
			Map<String, Object> result = oMapper.convertValue(_checkup, Map.class);
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date d1 = new Date(Long.parseLong(result.get("tanggalCheckup").toString()));
			Date d2 = new Date(Long.parseLong(result.get("tanggalCheckupBerikutnya").toString()));
			String date1 = simpleDateFormat.format(d1);
			String date2 = simpleDateFormat.format(d2);
			Date lahirBalita = z.getTanggalLahirBalita();
			Date tanggalCheckup = _checkup.getTanggalCheckup();
			result.remove("idBalita");
			result.remove("tanggalCheckup");
			result.remove("tanggalCheckupBerikutnya");
			result.put("tanggalCheckup", date1);
			result.put("tanggalCheckupBerikutnya", date2);
			result.put("idBalita",z.getIdBalita());
			result.put("umurBalita",getMonthsDifference(lahirBalita, tanggalCheckup));
			result.put("namaBalita",z.getNamaBalita());
			result.put("namaOrangTua",z.getIdUser().getNamaUser());
			result.put("nikBalita",z.getNikBalita());
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
	}
	
	  @DeleteMapping("/{id}")
	  public ResponseEntity<CheckUp> deleteTutorial(@PathVariable("id") Integer id) {
		  CheckUp x = checkupSer.delete(id);
		  
		  if(x==null) {
			  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		  }else {
			  return new ResponseEntity<>(HttpStatus.OK);
		  }

	  }
	  
	  @GetMapping("/balita/{id}")
	  public ResponseEntity<List<Map<String, Object>>> getByIdBalita(@PathVariable("id") Integer id){
		  List<CheckUp> data = checkupSer.getByIdBalita(id);
		  if(data==null) {
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }else {
			  ObjectMapper oMapper = new ObjectMapper();
			  @SuppressWarnings("unchecked")
			  List<Map<String, Object>> result = oMapper.convertValue(data, List.class);
			  List<Map<String, Object>> res = new ArrayList<>();
			  Integer count =0;
			  for (Map<String, Object> x : result) {
				  	CheckUp y = data.get(count);
				  	Balita z = y.getIdBalita();
					String pattern = "yyyy-MM-dd";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
					Date d1 = new Date(Long.parseLong(x.get("tanggalCheckup").toString()));
					Date d2 = new Date(Long.parseLong(x.get("tanggalCheckupBerikutnya").toString()));
					String date1 = simpleDateFormat.format(d1);
					String date2 = simpleDateFormat.format(d2);
					Date lahirBalita = z.getTanggalLahirBalita();
					Date tanggalCheckup = y.getTanggalCheckup();
					x.remove("idBalita");
					x.remove("tanggalCheckup");
					x.remove("tanggalCheckupBerikutnya");
					x.put("tanggalCheckup", date1);
					x.put("tanggalCheckupBerikutnya", date2);
					x.put("idBalita",z.getIdBalita());
					x.put("umurBalita",getMonthsDifference(lahirBalita, tanggalCheckup));
					x.put("namaBalita",z.getNamaBalita());
					x.put("namaOrangTua",z.getIdUser().getNamaUser());
					x.put("nikBalita",z.getNikBalita());
					res.add(x);
					count++;
			  }
			  return new ResponseEntity<>(res,HttpStatus.OK);  
		  }
		  
	  }
	  @GetMapping("/all")
	  public ResponseEntity<List<Map<String, Object>>> getAllWithoutBalita(){
		  
		  List<CheckUp> data = checkupSer.getAll();
		  if(data==null) {
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }else {
			  ObjectMapper oMapper = new ObjectMapper();
			  @SuppressWarnings("unchecked")
			  List<Map<String, Object>> result = oMapper.convertValue(data, List.class);
			  List<Map<String, Object>> res = new ArrayList<>();
			  Integer count = 0;
			  for (Map<String, Object> x : result) {
				  	CheckUp y = data.get(count);
				  	Balita z = y.getIdBalita();
					String pattern = "yyyy-MM-dd";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
					Date d1 = new Date(Long.parseLong(x.get("tanggalCheckup").toString()));
					Date d2 = new Date(Long.parseLong(x.get("tanggalCheckupBerikutnya").toString()));
					String date1 = simpleDateFormat.format(d1);
					String date2 = simpleDateFormat.format(d2);
					Date lahirBalita = z.getTanggalLahirBalita();
					Date tanggalCheckup = y.getTanggalCheckup();
					x.remove("idBalita");
					x.remove("tanggalCheckup");
					x.remove("tanggalCheckupBerikutnya");
					x.put("tanggalCheckup", date1);
					x.put("tanggalCheckupBerikutnya", date2);
					x.put("idBalita",z.getIdBalita());
					x.put("umurBalita",getMonthsDifference(lahirBalita, tanggalCheckup));
					x.put("namaBalita",z.getNamaBalita());
					x.put("namaOrangTua",z.getIdUser().getNamaUser());
					x.put("nikBalita",z.getNikBalita());
					res.add(x);
					count++;
			  }
			  return new ResponseEntity<>(res,HttpStatus.OK);
		  }
		  
		  
	  }
	  
	  @GetMapping("/{id}")
	  public ResponseEntity<Map<String, Object>> getCheckupByIdCheckup(@PathVariable("id") Integer id){
		  CheckUp data = checkupSer.getById(id);
		 
		  if(data==null) {
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }
		  else{
				Balita x = data.getIdBalita();
				ObjectMapper oMapper = new ObjectMapper();
				@SuppressWarnings("unchecked")
				Map<String, Object> result = oMapper.convertValue(data, Map.class);
				String pattern = "yyyy-MM-dd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				Date d1 = new Date(Long.parseLong(result.get("tanggalCheckup").toString()));
				Date d2 = new Date(Long.parseLong(result.get("tanggalCheckupBerikutnya").toString()));
				String date1 = simpleDateFormat.format(d1);
				String date2 = simpleDateFormat.format(d2);
				Date lahirBalita = x.getTanggalLahirBalita();
				Date tanggalCheckup = data.getTanggalCheckup();
				result.remove("idBalita");
				result.remove("tanggalCheckup");
				result.remove("tanggalCheckupBerikutnya");
				result.put("tanggalCheckup", date1);
				result.put("tanggalCheckupBerikutnya", date2);
				result.put("idBalita",x.getIdBalita());
				result.put("umurBalita",getMonthsDifference(lahirBalita, tanggalCheckup));
				result.put("namaBalita",x.getNamaBalita());
				result.put("namaOrangTua",x.getIdUser().getNamaUser());
				result.put("nikBalita",x.getNikBalita());
				return new ResponseEntity<>(result,HttpStatus.OK);
		  }
		  
	  }
	  @GetMapping("/graph/balita/{id}")
	  public ResponseEntity<?> getDataForGraph(@PathVariable("id") Integer id) { 
		  
		  List<CheckUp> data = checkupSer.getGraphByIdOrangTua(id);
		  List<Map<String, Object>> res = new ArrayList<>();
		  if(data==null) {
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }
		  Integer idBalita = data.get(0).getIdBalita().getIdBalita();
		  List<Float> dataBerat = new ArrayList<>();
		  List<Float> dataTinggi = new ArrayList<>();
		  List<Long> dataUmur = new ArrayList<>();
		  CheckUp lastData = new CheckUp();
		  for (CheckUp x : data) {

			  if(x.getIdBalita().getIdBalita()!=idBalita) {
				  Map<String, Object> result = new HashedMap<String, Object>();
				 
				  result.put("idBalita",lastData.getIdBalita().getIdBalita());
				  result.put("namaBalita",lastData.getIdBalita().getNamaBalita());
				  result.put("namaOrangTua",lastData.getIdBalita().getIdUser().getNamaUser());
				  result.put("nikBalita",lastData.getIdBalita().getNikBalita());
				  result.put("tinggiBalita", dataTinggi);
				  result.put("beratBalita", dataBerat);
				  result.put("umurBalita", dataUmur);
				  
				  res.add(result);
				  
				 dataTinggi = new ArrayList<>();
				 dataUmur = new ArrayList<>();
				 dataBerat = new ArrayList<>();	 

			  }
			  Date lahirBalita = x.getIdBalita().getTanggalLahirBalita();
			  Date tanggalCheckup = x.getTanggalCheckup();
			  idBalita=x.getIdBalita().getIdBalita();
			  dataBerat.add(x.getBeratBadan());
			  dataTinggi.add(x.getTinggiBadan());
			  dataUmur.add(getMonthsDifference(lahirBalita, tanggalCheckup));
			  lastData = x;
		  }
		  if(lastData.getIdBalita().getIdBalita()!=idBalita) {
			  Date lahirBalita = lastData.getIdBalita().getTanggalLahirBalita();
			  Date tanggalCheckup = lastData.getTanggalCheckup();
			  idBalita=lastData.getIdBalita().getIdBalita();
			  dataBerat.add(lastData.getBeratBadan());
			  dataTinggi.add(lastData.getTinggiBadan());
			  dataUmur.add(getMonthsDifference(lahirBalita, tanggalCheckup));
			  ObjectMapper oMapper = new ObjectMapper();
			  @SuppressWarnings("unchecked")
			  Map<String, Object> result = oMapper.convertValue(lastData, Map.class);
			  result.remove("idBalita");
			  result.remove("tanggalCheckup");
			  result.remove("tanggalCheckupBerikutnya");
			  result.remove("tinggiBadan");
			  result.remove("beratBadan");
			  result.remove("lingkarKepala");
			  result.remove("lingkarLengan");
			  result.remove("catatan");
			  result.remove("idCheckup");
			  
			  result.put("idBalita",lastData.getIdBalita().getIdBalita());
			  result.put("namaBalita",lastData.getIdBalita().getNamaBalita());
			  result.put("namaOrangTua",lastData.getIdBalita().getIdUser().getNamaUser());
			  result.put("nikBalita",lastData.getIdBalita().getNikBalita());
			  result.put("tinggiBalita", dataTinggi);
			  result.put("beratBalita", dataBerat);
			  result.put("umurBalita", dataUmur);
			  res.add(result);
		  }else {

			  ObjectMapper oMapper = new ObjectMapper();
			  @SuppressWarnings("unchecked")
			  Map<String, Object> result = oMapper.convertValue(lastData, Map.class);
			  result.remove("idBalita");
			  result.remove("tanggalCheckup");
			  result.remove("tanggalCheckupBerikutnya");
			  result.remove("tinggiBadan");
			  result.remove("beratBadan");
			  result.remove("lingkarKepala");
			  result.remove("lingkarLengan");
			  result.remove("catatan");
			  result.remove("idCheckup");
			  
			  result.put("idBalita",lastData.getIdBalita().getIdBalita());
			  result.put("namaBalita",lastData.getIdBalita().getNamaBalita());
			  result.put("namaOrangTua",lastData.getIdBalita().getIdUser().getNamaUser());
			  result.put("nikBalita",lastData.getIdBalita().getNikBalita());
			  result.put("tinggiBalita", dataTinggi);
			  result.put("beratBalita", dataBerat);
			  result.put("umurBalita", dataUmur);
			  res.add(result);
		  }

		  return new ResponseEntity<>(res,HttpStatus.OK);
		  
		  
	  }
	  
	  @Scheduled(cron = "${cronExpresCheckup}")
	  @GetMapping("/reminder")
	  public void sendReminderForCheckup() {
		  List<CheckupInterface> res = checkupSer.getForReminder();
		  
		  for (CheckupInterface x : res) {
			  Balita balita = balitaServ.getById(x.getIdBalita());
			  UserPosyandu user = balita.getIdUser();
			  
			  whatServ.sendReminderCheckup(user,balita);

		  }
	  }
	  
	  @GetMapping("/user/{id}")
	  public ResponseEntity<List<Map<String, Object>>> getDataforGraphbyIdOrangTua(@PathVariable("id") Integer id) {
		  List<CheckUp> data = checkupSer.getGraphByIdOrangTua(id);
		  
		  if(data==null) {
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }
		  ObjectMapper oMapper = new ObjectMapper();
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> result = oMapper.convertValue(data, List.class);
			List<Map<String, Object>> res = new ArrayList<>();
			Integer count = 0;
			for (Map<String, Object> x : result) {
				CheckUp dataCheckup = data.get(count);
				Balita z = dataCheckup.getIdBalita();
				String pattern = "yyyy-MM-dd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				Date d1 = new Date(Long.parseLong(x.get("tanggalCheckup").toString()));
				Date d2 = new Date(Long.parseLong(x.get("tanggalCheckupBerikutnya").toString()));
				String date1 = simpleDateFormat.format(d1);
				String date2 = simpleDateFormat.format(d2);
				Date lahirBalita = z.getTanggalLahirBalita();
				Date tanggalCheckup = dataCheckup.getTanggalCheckup();
				x.remove("idBalita");
				x.remove("tanggalCheckup");
				x.remove("tanggalCheckupBerikutnya");
				x.put("tanggalCheckup", date1);
				x.put("tanggalCheckupBerikutnya", date2);
				x.put("idBalita",z.getIdBalita());
				x.put("umurBalita",getMonthsDifference(lahirBalita, tanggalCheckup));
				x.put("namaBalita",z.getNamaBalita());
				x.put("namaOrangTua",z.getIdUser().getNamaUser());
				x.put("nikBalita",z.getNikBalita());
				
				res.add(x);
				count++;
				
			}
		  
			return new ResponseEntity<>(res,HttpStatus.OK);
	  }
	  
	  public static final long getMonthsDifference(Date date1, Date date2) {
		    YearMonth m1 = YearMonth.from(date1.toInstant().atZone(ZoneOffset.UTC));
		    YearMonth m2 = YearMonth.from(date2.toInstant().atZone(ZoneOffset.UTC));

		    return m1.until(m2, ChronoUnit.MONTHS);
		}
}

