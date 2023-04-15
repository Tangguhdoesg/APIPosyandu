package com.skirpsi.api.posyandu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skirpsi.api.posyandu.misc.SfaBoys0to5;
import com.skirpsi.api.posyandu.misc.SfaGirl0to5;
import com.skirpsi.api.posyandu.misc.WfaBoy0to5;
import com.skirpsi.api.posyandu.misc.WfaGirl0to5;
import com.skirpsi.api.posyandu.repository.SfaBoysRepository;
import com.skirpsi.api.posyandu.repository.SfaGirlsRepository;
import com.skirpsi.api.posyandu.repository.WfaBoysRepository;
import com.skirpsi.api.posyandu.repository.WfaGirlsRepository;

@Service
public class WfaSfaDataService {
	
	@Autowired SfaBoysRepository sfaBoysRepo;
	
	@Autowired SfaGirlsRepository sfaGirlRepo;
	
	@Autowired WfaGirlsRepository wfaGirlRepo;
	
	@Autowired WfaBoysRepository wfaBoysRepo;
	
	public List<SfaBoys0to5> sizeForAgesBoys(){
		return sfaBoysRepo.findAll();
	}
	
	public List<SfaGirl0to5> sizeForAgesGirls(){
		return sfaGirlRepo.findAll();
	}
	
	public List<WfaBoy0to5> weightForAgesBoys(){
		return wfaBoysRepo.findAll();
	}
	
	public List<WfaGirl0to5> weightForAgesGirls(){
		return wfaGirlRepo.findAll();
	}

}
