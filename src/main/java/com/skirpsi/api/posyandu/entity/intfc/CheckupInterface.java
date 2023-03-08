package com.skirpsi.api.posyandu.entity.intfc;

import java.sql.Timestamp;

public interface CheckupInterface {
	
	Integer getIdBalita();
	Integer getIdOrangTua();
	Integer getIdcheckup();
	Integer getTinggibadan(); 
	String getNamaBalita();
	Float getBeratbadan(); 
	Float getLingkarkepala(); 
	Float getLingkarlengan();
	Timestamp getTanggalcheckup();
	Timestamp getTanggalcheckupberikutnya(); 
	String getCatatancheckup();

}
