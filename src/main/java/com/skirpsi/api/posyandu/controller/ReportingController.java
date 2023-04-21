package com.skirpsi.api.posyandu.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skirpsi.api.posyandu.misc.CreateReport;
import com.skirpsi.api.posyandu.service.ReportService;
import com.skirpsi.api.posyandu.service.WfaSfaDataService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("report")
public class ReportingController {
	@Value("${spring.mail.username}") private String sender;
	
	@Autowired private JavaMailSender javaMailSender;
	@Autowired ReportService reportServ;
	
	@Autowired WfaSfaDataService wfaSfaSer;
	
	@PostMapping("/excelCheckupOld")
	public ResponseEntity<byte[]> generateExcelCheckupOld(@RequestBody CreateReport report) {
		File file = reportServ.createCheckupReportCheckup(report.getTanggalAwal(), report.getTanggalAkhir());
		if(file==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	    try {
			return ResponseEntity.ok()
			        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
			        .body(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@PostMapping("/excelCheckup")
	public ResponseEntity<Resource> generateExcelCheckup(@RequestBody CreateReport report) {
		File file = reportServ.createCheckupReportCheckup(report.getTanggalAwal(), report.getTanggalAkhir());
		if(file==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	    try {
	        Resource fileResource = new UrlResource(file.toURI());
	        Path path = fileResource.getFile()
	                        .toPath();
	    	return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                    .body(fileResource);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@PostMapping("/excelImunisasi")
	public ResponseEntity<Resource> generateExcelImunisasi(@RequestBody CreateReport report) {  
		File file = reportServ.createCheckupReportImunisasi(report.getTanggalAwal(),report.getTanggalAkhir());
		if(file==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	    try {
	        Resource fileResource = new UrlResource(file.toURI());
	        Path path = fileResource.getFile()
	                        .toPath();
	    	return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                    .body(fileResource);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	
	@PostMapping("/send")
	public ResponseEntity<String> sendMail(@RequestBody CreateReport report) {
		File file = reportServ.createCheckupReportImunisasi(report.getTanggalAwal(),report.getTanggalAkhir());
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		
		System.out.println(report.getTanggalAkhir());
		System.out.println(report.getTanggalAwal());
		System.out.println(report.getEmail());
		
		try {
			mimeMessageHelper= new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(report.getEmail());
			mimeMessageHelper.setText("Dear DINKES HERE ARE YOUR DATA");
			mimeMessageHelper.setSubject("DATA BALITA");
			mimeMessageHelper.addAttachment(file.getName(), file);

			javaMailSender.send(mimeMessage);
			
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (MessagingException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
