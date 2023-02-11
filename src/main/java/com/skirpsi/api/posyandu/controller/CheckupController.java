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
import com.skirpsi.api.posyandu.entity.CheckUp;
import com.skirpsi.api.posyandu.entity.intfc.CheckupInterface;
import com.skirpsi.api.posyandu.service.CheckupService;

@RestController
@RequestMapping("checkup")
public class CheckupController {
	
	@Autowired CheckupService checkupSer;
	
	@GetMapping("/all")
	public ResponseEntity<List<CheckUp>> getAll(){
		List<CheckUp> all = checkupSer.getAll();
		
		if(all.size()>0) {
			return new ResponseEntity<>(all,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
		
	}
	
	@GetMapping("/{id}")
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
			_checkup.setBalita(checkup.getBalita());
			_checkup.setBeratBadan(checkup.getBeratBadan());
			_checkup.setLingkarKepala(checkup.getLingkarKepala());
			_checkup.setLingkarLengan(checkup.getLingkarLengan());
			_checkup.setTanggalCheckup(checkup.getTanggalCheckup());
			_checkup.setTanggalCheckupBerikutnya(checkup.getTanggalCheckupBerikutnya());
			_checkup.setTinggibadan(checkup.getTinggibadan());
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
	  
	  @GetMapping("/balita")
	  public ResponseEntity<List<CheckUp>> getByBalita(@RequestBody Balita b){
		  List<CheckUp> data = checkupSer.getByBalita(b);
		  
		  return new ResponseEntity<>(data,HttpStatus.OK);
	  }
	  
	  @GetMapping("/balita/{id}")
	  public ResponseEntity<List<CheckupInterface>> getByIdBalita(@PathVariable("id") Integer id){
		  
		  List<CheckupInterface> data = checkupSer.getByIdBalita(id);
		  
		  return new ResponseEntity<List<CheckupInterface>>(data,HttpStatus.OK);
		  
	  }
}
