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
import com.skirpsi.api.posyandu.entity.CheckUp;
import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.entity.intfc.BalitaInterface;
import com.skirpsi.api.posyandu.entity.intfc.CheckupInterface;
import com.skirpsi.api.posyandu.entity.intfc.UserInterface;
import com.skirpsi.api.posyandu.service.BalitaService;
import com.skirpsi.api.posyandu.service.CheckupService;
import com.skirpsi.api.posyandu.service.ReportService;
import com.skirpsi.api.posyandu.service.UserService;
import com.skirpsi.api.posyandu.service.WhatsappService;

@RestController
@RequestMapping("checkup")
public class CheckupController {
	
	@Autowired CheckupService checkupSer;
	
	@Autowired WhatsappService whatServ;
	
	@Autowired UserService userServ;
	
	@Autowired BalitaService balitaServ;
	
	@Autowired ReportService reportServ;
	
	@GetMapping("/balita/all")
	public ResponseEntity<List<CheckUp>> getAll(){
		List<CheckUp> all = checkupSer.getAll();
		
		if(all.size()>0) {
			return new ResponseEntity<>(all,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
		
	}
	
	@GetMapping("/balita/{id}")
	public ResponseEntity<CheckUp> getCheckup(@PathVariable("id") Integer id){
		CheckUp data = checkupSer.getById(id);
		
		if(data==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}
//	
	@PostMapping()
	public ResponseEntity<CheckUp> createCheckup(@RequestBody CheckUp checkup){
			CheckUp data = checkupSer.insert(checkup);
			
			if(data==null) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}else{
				return new ResponseEntity<>(data,HttpStatus.OK);
			}
	}
//	
	@PutMapping("/{id}")
	public ResponseEntity<CheckUp> updateCheckup(@RequestBody CheckUp checkup,@PathVariable("id") Integer id){
		CheckUp _checkup = checkupSer.getById(id);
		
		if(_checkup==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			_checkup.setIdBalita(checkup.getIdBalita());
			_checkup.setBeratBadan(checkup.getBeratBadan());
			_checkup.setLingkarKepala(checkup.getLingkarKepala());
			_checkup.setLingkarLengan(checkup.getLingkarLengan());
			_checkup.setTanggalCheckup(checkup.getTanggalCheckup());
			_checkup.setTanggalCheckupBerikutnya(checkup.getTanggalCheckupBerikutnya());
			_checkup.setTinggiBadan(checkup.getTinggiBadan());
			_checkup.setCatatan(checkup.getCatatan());
			
			checkupSer.insert(_checkup);
			
			return new ResponseEntity<>(_checkup,HttpStatus.OK);
		}
	}
//	
	  @DeleteMapping("/{id}")
	  public ResponseEntity<CheckUp> deleteTutorial(@PathVariable("id") Integer id) {
		  CheckUp x = checkupSer.delete(id);
		  
		  if(x==null) {
			  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		  }else {
			  return new ResponseEntity<>(HttpStatus.OK);
		  }

	  }
	  
	  @GetMapping("/{id}")
	  public ResponseEntity<List<CheckupInterface>> getByIdBalita(@PathVariable("id") Integer id){
		  
		  List<CheckupInterface> data = checkupSer.getByIdBalita(id);
		  
		  return new ResponseEntity<>(data,HttpStatus.OK);  
	  }
	  @GetMapping("/all")
	  public ResponseEntity<List<CheckupInterface>> getAllWithoutBalita(){
		  List<CheckupInterface> data = checkupSer.getAllWithoutBalita();
		  
		  return new ResponseEntity<>(data,HttpStatus.OK);
	  }
	  
	  @Scheduled(cron = "0 7 * * *")
	  @GetMapping("/reminder")
	  public void sendReminderForCheckup() {
		  List<CheckupInterface> res = checkupSer.getForReminder();
		  
		  System.out.println();
		  
		  for (CheckupInterface x : res) {
			  Balita balita = balitaServ.getById(x.getIdBalita());
			  UserPosyandu user = balita.getIdUser();
			  
			  whatServ.sendReminder(user,balita);
			  System.out.println("DONE");

		  }
	  }
	  
	  @GetMapping("/excel")
	  public void generateExcel() {
		  
		  
		  String dateFrom = "2023-02-01";
		  String dateTo = "2023-02-28";
		  reportServ.createCheckupReport(dateFrom, dateTo);
	  }
}
