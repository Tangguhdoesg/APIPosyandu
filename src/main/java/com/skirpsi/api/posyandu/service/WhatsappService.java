package com.skirpsi.api.posyandu.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class WhatsappService {
	@Value("${trilio.user}")
	private String usertrilio;
	
	@Value("${trilio.token}")
	private String token;
	
	public void testSendAPI() {
		
		Twilio.init(usertrilio, token);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+6287854472001"),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                "Welcome to Pelita from Trillo. Here are your password for this.")
            .create();

        System.out.println(message.getSid());
	}
	
	public void sendPassword(UserPosyandu user){
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Twilio.init(usertrilio, token);
//		String telpUser = "+62"+user.getNoTeleponUser().substring(1,user.getNoTeleponUser().length());
		String telpUser = "+6287854472001";
		String passwordUser=simpleDateFormat.format(user.getTanggalLahirUser());
		String templateMessage = "Selamat Datang di Aplikasi Pelita.\r\n"
				+ "Aplikasi ini dapat membantu anda untuk memantau informasi kesehatan balita anda.\r\n"
				+ "Gunakan nomor telepon ini untuk login kedalam aplikasi pada web : PELITA.COM\r\n"
				+ "Password yang dapat anda gunakan adalah sebagai berikut : \r\n"
				+ passwordUser+"\r\n"
				+ "Mohon tidak menshare password ini dan jaga selalu keamanan password tersebut.\r\n"
				+ "Terimakasih.";
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:"+telpUser),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                templateMessage)
            .create();
	}
	
	public void sendReminder(UserPosyandu user,Balita balita) {
		Twilio.init(usertrilio, token);

		LocalDate now = LocalDate.now().plusDays(1);
		Date date = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Locale locale = new Locale("id", "ID");
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy", locale);
		String formatted = dateFormat.format(date);
//		System.out.println(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(now));
//		String tanggal =  DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(now);
//		String telpUser = "+62"+user.getNoTeleponUser().substring(1,user.getNoTeleponUser().length());
		String telpUser = "+6287854472001";
		String templateMessage="Selamat Pagi, "+user.getNamaUser()+"\r\n"
				+ "Mohon Melakukan Checkup pada Posyandu X dengan membawa balita "+ balita.getNamaBalita()+"\r\n"
				+ "Pada Tanggal : "+formatted+"\r\n"
				+ "Pukul : 08.00 WIB\r\n"
				+ "Mohon membawa seluruh persyaratan checkup.\r\n"
				+ "Sekian dan Terimakasih.";
		System.out.println(templateMessage);
//        Message message = Message.creator(
//                new com.twilio.type.PhoneNumber("whatsapp:"+telpUser),
//                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
//                templateMessage)
//            .create();
	}
}
