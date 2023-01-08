package com.skirpsi.api.posyandu.controller;

import java.util.Optional;

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

import com.skirpsi.api.posyandu.entity.CheckUp;
import com.skirpsi.api.posyandu.repository.CheckupRepository;

@RestController
@RequestMapping("checkup")
public class CheckupController {
	
	@Autowired CheckupRepository checkupRepo;
	
	@GetMapping("/getone")
	public ResponseEntity<CheckUp> testGet(){
		CheckUp x = checkupRepo.findById(1).get();
		
		try {
			return new ResponseEntity<>(x, HttpStatus.OK);	
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CheckUp> getCheckup(@PathVariable("id") Integer id){
		Optional<CheckUp> checkupData = checkupRepo.findById(id);
		
		if(checkupData.isPresent()) {
			return new ResponseEntity<>(checkupData.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping()
	public ResponseEntity<CheckUp> createCheckup(@RequestBody CheckUp checkup){
		try {
			CheckUp _checkup = new CheckUp();
			_checkup.setBalita(checkup.getBalita());
			_checkup.setBeratBadan(checkup.getBeratBadan());
			_checkup.setLingkarKepala(checkup.getLingkarKepala());
			_checkup.setLingkarLengan(checkup.getLingkarLengan());
			_checkup.setTanggalCheckup(checkup.getTanggalCheckup());
			_checkup.setTanggalCheckupBerikutnya(checkup.getTanggalCheckupBerikutnya());
			_checkup.setTinggibadan(checkup.getTinggibadan());
			checkupRepo.save(_checkup);
			return new ResponseEntity<>(_checkup,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CheckUp> updateCheckup(@RequestBody CheckUp checkup,@PathVariable("id") Integer id){
		Optional<CheckUp> checkupData = checkupRepo.findById(id);
		if(checkupData.isPresent()) {
			try {
				CheckUp _checkup = checkupData.get();
				_checkup.setBalita(checkup.getBalita());
				_checkup.setBeratBadan(checkup.getBeratBadan());
				_checkup.setLingkarKepala(checkup.getLingkarKepala());
				_checkup.setLingkarLengan(checkup.getLingkarLengan());
				_checkup.setTanggalCheckup(checkup.getTanggalCheckup());
				_checkup.setTanggalCheckupBerikutnya(checkup.getTanggalCheckupBerikutnya());
				_checkup.setTinggibadan(checkup.getTinggibadan());
				checkupRepo.save(_checkup);
				return new ResponseEntity<>(_checkup,HttpStatus.OK);
			} catch (Exception e) {
				// TODO: handle exception
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	  @DeleteMapping("/{id}")
	  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") Integer id) {
		  Optional<CheckUp>  checkupData = checkupRepo.findById(id);
		  if(checkupData.isPresent()) {
			  checkupRepo.deleteById(id);
		    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		  }else {
			  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  }

	  }
}
