package com.skirpsi.api.posyandu.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skirpsi.api.posyandu.misc.CreateReport;
import com.skirpsi.api.posyandu.service.ReportService;

@RestController
@RequestMapping("report")
public class ReportingController {
	@Value("${spring.mail.username}") private String sender;
	
	@Autowired private JavaMailSender javaMailSender;
	@Autowired ReportService reportServ;
	
	@GetMapping("/excelCheckup")
//	public ResponseEntity<byte[]> generateExcelCheckup(@RequestBody CreateReport report) {
	public ResponseEntity<byte[]> generateExcelCheckup() {  
//		File file = reportServ.createCheckupReportCheckup(report.getTanggalAwal(), report.getTanggalAkhir());
		File file = reportServ.createCheckupReportCheckup("2023-01-01", "2023-04-10");
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
	
	@GetMapping("/excelImunisasi")
//	public ResponseEntity<byte[]> generateExcelImunisasi(@RequestBody CreateReport report) {  
//		File file = reportServ.createCheckupReportImunisasi(report.getTanggalAwal(), report.getTanggalAkhir());
	public ResponseEntity<byte[]> generateExcelImunisasi() {  
		File file = reportServ.createCheckupReportImunisasi("2023-01-01", "2023-04-10");
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
	
	
	@GetMapping("/send")
	public void sendMail() {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		
		try {
			mimeMessageHelper= new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo("tangguhdoesgaming@gmail.com");
			mimeMessageHelper.setText("Dear DINKES HERE ARE YOUR DATA");
			mimeMessageHelper.setSubject("DATA BALITA");
			FileSystemResource file = new FileSystemResource(new File("D:/random/excelTest.xlsx"));
			mimeMessageHelper.addAttachment(file.getFilename(), file);

			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
