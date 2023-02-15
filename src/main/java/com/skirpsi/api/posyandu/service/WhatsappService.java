package com.skirpsi.api.posyandu.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.SimpleDateFormat;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class WhatsappService {
	
//    public static final String ACCOUNT_SID = System.getenv("AC1a47f7cce3e43a519d9c7452411e7387");
//    public static final String AUTH_TOKEN = System.getenv("12c39c97dbc31723e67f951226aa6cc0");
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
//		try {
//			HttpRequest req = HttpRequest.newBuilder()
//					.uri(new URI("https://graph.facebook.com/v13.0/62-811-3704-895/messages"))
//					.header("Authorization", "Bearer EAAH9sphjag4BAMjw53Ra6NWfdKCE1zNawOgZCkr9q3bYDDmXslZAAZAYTRhCYFnluuAm44DFHYhcuz18LDplEJlft203fRIGVr4OvO2ICnXZCdjnQwamKcgbHo8kIyqsm6aiIEeZCqukN1jkkq5LgyZBMz2iA0dCT43gs86Xv5AK2XelxseAuNVZAEd5qhVNgDlKRxSDbWYtbzOd5SZAqUGTiamnoy5YpZBsZD").header("Content-Type", "application/json")
//					.POST(HttpRequest.BodyPublishers.ofString(
////							"{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \"+6287854472001\", \"type\": \"template\", \"template\": { \"name\": \"hello_world\", \"language\": { \"code\": \"en_US\" } } }"))
//							"{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \"+6287854472001\", \"type\": \"text\", \"text\": { // the text object\r\n"
//							+ "        \"preview_url\": false, "
//							+ "        \"body\": \"WELCOME TO PELTIA, HERE ARE YOUR CRED. \" "
//							+ "        } }"))
//					.build();
//			HttpClient http = HttpClient.newHttpClient();
//			HttpResponse<String> response = http.send(req, BodyHandlers.ofString());
//			System.out.println(response.body());
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("ERROR " + e);
//		}
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
