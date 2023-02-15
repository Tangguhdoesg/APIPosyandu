package com.skirpsi.api.posyandu.service;

import java.text.SimpleDateFormat;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		Twilio.init(usertrilio, token);
		String telpUser = "+62"+user.getNoTeleponUser().substring(1,user.getNoTeleponUser().length());
		String passwordUser=sdf.format(user.getTanggalLahirUser());
		String passwordUserEncode = Base64.getEncoder().encodeToString(passwordUser.getBytes());
		String templateMessage = "Selamat Datang di Aplikasi Pelita.\r\n"
				+ "Aplikasi ini dapat membantu anda untuk memantau informasi kesehatan balita anda.\r\n"
				+ "Gunakan nomor telepon ini untuk login kedalam aplikasi pada web : ....\r\n"
				+ "Password yang dapat anda gunakan adalah sebagai berikut : \r\n"
				+ passwordUser+"\r\n"
				+ "Mohon tidak menshare password ini dan jaga selalu keamanan password tersebut.\r\n"
				+ "Terimakasih.";
//		System.out.println(telpUser);
		System.out.println(templateMessage);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:"+telpUser),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                templateMessage)
            .create();
	}
}
