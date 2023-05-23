package com.skirpsi.api.posyandu.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.skirpsi.api.posyandu.entity.CheckUp;
import com.skirpsi.api.posyandu.misc.CreateReport;
import com.skirpsi.api.posyandu.service.ReportService;
import com.skirpsi.api.posyandu.service.WfaSfaDataService;
import com.twilio.rest.api.v2010.account.availablephonenumbercountry.Local;

@CrossOrigin(origins = {"http://localhost:4200", "https://aplikasi-posyandu.vercel.app"},maxAge = 3600)
@RestController
@RequestMapping("report")
public class ReportingController {
	@Value("${spring.mail.username}") private String sender;
	
	@Autowired private JavaMailSender javaMailSender;
	@Autowired ReportService reportServ;
	@Autowired WfaSfaDataService whoService;
	@Autowired WfaSfaDataService wfaSfaSer;
	
	@PostMapping("/excelCheckup")
	public ResponseEntity<Resource> generateExcelCheckup(@RequestBody CreateReport report) {
		File file = reportServ.createCheckupReportCheckup(report.getTanggalAwal(), report.getTanggalAkhir());
		if(file==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

        Path path = Paths.get(file.getAbsolutePath());
		try {
			ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.toPath()));
			HttpHeaders head= new HttpHeaders();
			List<String> customHeader = new ArrayList<>();
			customHeader.add("INI-PUNYA-RAFLI");
			customHeader.add("Content-Disposition");
	        head.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path));
	        head.add(HttpHeaders.CONTENT_DISPOSITION, "" + file.getName());
	        head.add("filename",file.getName());
	        head.setAccessControlExposeHeaders(customHeader);
	    	return ResponseEntity.ok().headers(head).body(resource);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@PostMapping("/excelImunisasi")
	public ResponseEntity<Resource> generateExcelImunisasi(@RequestBody CreateReport report) {  
		File file = reportServ.createCheckupReportImunisasi(report.getTanggalAwal(),report.getTanggalAkhir());
		if(file==null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		Path path = Paths.get(file.getAbsolutePath());
		try {
			ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.toPath()));
			HttpHeaders head= new HttpHeaders();
			List<String> customHeader = new ArrayList<>();
			customHeader.add("Content-Disposition");
	        head.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path));
	        head.add(HttpHeaders.CONTENT_DISPOSITION, "" + file.getName());
	        head.add("filename",file.getName());
	        head.setAccessControlExposeHeaders(customHeader);
	    	return ResponseEntity.ok().headers(head).body(resource);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	@PostMapping("/send")
	public ResponseEntity<String> sendMail(@RequestBody CreateReport report) {
		File file = reportServ.createCheckupReportImunisasi(report.getTanggalAwal(),report.getTanggalAkhir());
		File fileImunisasi = reportServ.createCheckupReportCheckup(report.getTanggalAwal(), report.getTanggalAkhir());
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		
		try {
			mimeMessageHelper= new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(report.getEmail());
			mimeMessageHelper.setText("Kepada Bapak/Ibu \r\n Berikut terlampir data checkup dan imunisasi Posyandu Anggrek periode " + report.getTanggalAwal()+ " hingga " + report.getTanggalAkhir()+".\r\n"+"Sekian dan Terimakasih \r\n\r\n\r\n Posyandu Anggrek");
			mimeMessageHelper.setSubject("Data Checkup dan Imunisasi Posyandu Anggrek.");
			mimeMessageHelper.addAttachment(file.getName(), file);
			mimeMessageHelper.addAttachment(fileImunisasi.getName(), fileImunisasi);
			javaMailSender.send(mimeMessage);
			
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (MessagingException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/summary")
	public Map<String, Object> summaryOfReport(){
		Integer checkup30 = getCheckup30();
		Integer imunisasi30 = getImunisasi30();
		List<Integer>sehatTidakSehat = getTotalSehat();
		Integer sehat = sehatTidakSehat.get(0);
		Integer tidakSehat = sehatTidakSehat.get(1);
		
		List<Integer> ret = new ArrayList<>();
		
		ret.add(checkup30);
		ret.add(imunisasi30);
		ret.add(sehat);	
		ret.add(tidakSehat);
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate today = LocalDate.now();
		LocalDate day30 = LocalDate.now().minusDays(30);
		
		Map<String, Object> returnData = new HashedMap<>();
		returnData.put("ListNumber", ret);
		returnData.put("start", today.format(formatters));
		returnData.put("end", day30.format(formatters));
		return returnData;
	}
	
	@GetMapping("/checkup30")
	public Integer getCheckup30 () {
		return reportServ.getCheckupLast30DaysData();
	}
	
	@GetMapping("/imunisasi30")
	public Integer getImunisasi30() {
		return reportServ.getImunisasiLast30DaysData();
	}
	
	@GetMapping("/sehat")
	public List<Integer> getTotalSehat() {
		List<Integer> ret = new ArrayList<>();
		Integer countSehat = 0;
		Integer countTidakSehat=0;
		LocalDate now = LocalDate.now();
		LocalDate last30 = now.minusDays(30);
		List<CheckUp> data = reportServ.getDataForSehat(last30.toString(),now.toString());
		for (CheckUp x : data ) {
			Date lahirBalita = x.getIdBalita().getTanggalLahirBalita();
			Date tanggalCheckup = x.getTanggalCheckup();
			Long umur = getMonthsDifference(lahirBalita, tanggalCheckup);
			if(x.getIdBalita().getJenisKelaminBalita().contains("Laki")) {
				Float batasAtas = whoService.sizeForAgesBoys().get(umur.intValue()).getPost2sd();
				Float batasBawah = whoService.sizeForAgesBoys().get(umur.intValue()).getNeg2sd();
				if(x.getBeratBadan()>=batasBawah&&x.getBeratBadan()<= batasAtas) {
//					sehat
					countSehat++;
				}else {
//					tidak sehat
					countTidakSehat++;
				}
			}else {
				Float batasAtas = whoService.sizeForAgesGirls().get(umur.intValue()).getPost2sd();
				Float batasBawah = whoService.sizeForAgesGirls().get(umur.intValue()).getNeg2sd();
				if(x.getBeratBadan()>=batasBawah&&x.getBeratBadan()<= batasAtas) {
//					sehat
					countSehat++;
				}else {
//					tidak sehat
					countTidakSehat++;
				}
			}
			
		}
		ret.add(countSehat);
		ret.add(countTidakSehat);		
		return ret;
	}
	
	  public static final long getMonthsDifference(Date date1, Date date2) {
		    YearMonth m1 = YearMonth.from(date1.toInstant().atZone(ZoneOffset.UTC));
		    YearMonth m2 = YearMonth.from(date2.toInstant().atZone(ZoneOffset.UTC));

		    return m1.until(m2, ChronoUnit.MONTHS);
		}
	
}
