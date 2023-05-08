package com.skirpsi.api.posyandu.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.skirpsi.api.posyandu.entity.Balita;
import com.skirpsi.api.posyandu.entity.UserPosyandu;
import com.skirpsi.api.posyandu.entity.intfc.KegiatanInterface;
import com.skirpsi.api.posyandu.entity.intfc.UserInterface;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

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
		String telpUser = "+6287854472001";
		String passwordUser=simpleDateFormat.format(user.getTanggalLahirUser());
		String templateMessage = "Selamat Datang di Aplikasi Pelita.\r\n"
				+ "Aplikasi ini dapat membantu anda untuk memantau informasi kesehatan balita anda.\r\n"
				+ "Gunakan nomor telepon ini untuk login kedalam aplikasi pada web : PELITA.COM\r\n"
				+ "Password yang dapat anda gunakan adalah sebagai berikut : \r\n"
				+ passwordUser+"\r\n"
				+ "Mohon tidak menshare password ini dan jaga selalu keamanan password tersebut.\r\n"
				+ "Terimakasih.";
        Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:"+telpUser),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                templateMessage)
            .create();
	}
	
	public void sendReminderCheckup(UserPosyandu user,Balita balita) {
		Twilio.init(usertrilio, token);

		LocalDate now = LocalDate.now().plusDays(1);
		Date date = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Locale locale = new Locale("id", "ID");
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy", locale);
		String formatted = dateFormat.format(date);
		String telpUser = "+6287854472001";
		String templateMessage="Selamat Pagi, "+user.getNamaUser()+"\r\n"
				+ "Mohon Melakukan Checkup pada Posyandu Anggrek dengan membawa balita "+ balita.getNamaBalita()+"\r\n"
				+ "Pada Tanggal : "+formatted+"\r\n"
				+ "Pukul : 08.00 WIB\r\n"
				+ "Mohon membawa seluruh persyaratan checkup seperti buku KIA.\r\n"
				+ "Sekian dan Terimakasih.";
		 Message.creator(
	                new PhoneNumber("whatsapp:"+telpUser),
	                new PhoneNumber("whatsapp:+14155238886"),
	                templateMessage)
	            .create();
		System.out.println(templateMessage);
	}
	
	public void sendReminderImunisasi(UserPosyandu user, Balita balita) {
		Twilio.init(usertrilio, token);

		LocalDate now = LocalDate.now().plusDays(1);
		Date date = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Locale locale = new Locale("id", "ID");
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy", locale);
		String formatted = dateFormat.format(date);
		String telpUser = "+6287854472001";
		String templateMessage="Selamat Pagi, "+user.getNamaUser()+"\r\n"
				+ "Mohon Melakukan Imunisasi pada Posyandu Anggrek dengan membawa balita "+ balita.getNamaBalita()+"\r\n"
				+ "Pada Tanggal : "+formatted+"\r\n"
				+ "Pukul : 08.00 WIB\r\n"
				+ "Mohon membawa seluruh persyaratan checkup seperti Buku KIA.\r\n"
				+ "Sekian dan Terimakasih.";
		 Message.creator(
	                new PhoneNumber("whatsapp:"+telpUser),
	                new PhoneNumber("whatsapp:+14155238886"),
	                templateMessage)
	            .create();
			System.out.println(templateMessage);
	}
	
	public void sendReminderKegiatan(UserInterface user,KegiatanInterface kegiatan) {
		Twilio.init(usertrilio, token);

		LocalDate now = LocalDate.now().plusDays(1);
		Date date = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Locale locale = new Locale("id", "ID");
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy", locale);
		String formatted = dateFormat.format(date);
		String telpUser = "+6287854472001";
		String templateMessage="Selamat Pagi, "+user.getNamaUser()+"\r\n"
				+ "Kami ingin menginformasikan bahwa akan ada kegiatan yang berjudul : "+kegiatan.getNamaKegiatan()+"\r\n"
				+ "Kegiatan ini akan berlangsung pada : "
				+ "Tanggal : " + formatted
				+ "Pukul : 08.00 WIB\r\n"
				+ "Apabila Bapak / Ibu tertarik dengan kegiatan tersebut maka dapat langsung datang ke \r\n" + kegiatan.getLokasiKegiatan()
				+ "Untuk informasi lebih"
				+ "Sekian dan Terimakasih.";
		Message.creator(
	               new PhoneNumber("whatsapp:"+telpUser),
	               new PhoneNumber("whatsapp:+14155238886"),
	               templateMessage)
	           .create();
		System.out.println(templateMessage);
	}
}
