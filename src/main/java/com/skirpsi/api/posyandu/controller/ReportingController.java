package com.skirpsi.api.posyandu.controller;

import java.io.File;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skirpsi.api.posyandu.service.ReportService;

@RestController
@RequestMapping("report")
public class ReportingController {
	@Value("${spring.mail.username}") private String sender;
	
	@Autowired private JavaMailSender javaMailSender;
	@Autowired ReportService reportServ;
	@GetMapping("/excel")
	public void generateExcel() {  
		String dateFrom = "2023-02-01";
		String dateTo = "2023-02-28";
		reportServ.createCheckupReport(dateFrom, dateTo);
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
