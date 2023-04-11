package com.skirpsi.api.posyandu.service;

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
	
	public void test() {
		SfaBoys0to5 data = sfaBoysRepo.findById(2).get();
		
		System.out.println(data.getMonths());
		System.out.println(data.getMedian());
		System.out.println("===========");
		
		SfaGirl0to5 datagirl= sfaGirlRepo.findById(2).get();
		System.out.println(datagirl.getMedian());
		System.out.println(datagirl.getPost2sd());
		System.out.println("=========");
		
		WfaGirl0to5 dataWfaGirl = wfaGirlRepo.findById(2).get();
		System.out.println(dataWfaGirl.getMedian());
		System.out.println(dataWfaGirl.getPost3sd());
		System.out.println("=========");
		
		WfaBoy0to5 dataWfaBoys = wfaBoysRepo.findById(2).get();
		System.out.println(dataWfaBoys.getMedian());
		System.out.println(dataWfaBoys.getPost2sd());
		System.out.println("========");
		
		
	}

}
