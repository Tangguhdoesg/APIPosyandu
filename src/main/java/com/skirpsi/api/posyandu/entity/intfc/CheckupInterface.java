package com.skirpsi.api.posyandu.entity.intfc;

import java.sql.Timestamp;

public interface CheckupInterface {
	
	Integer getIdBalita();
	Integer getIdcheckup();
	Integer getTinggibadan(); 
	Integer getBeratbadan(); 
	Integer getLingkarkepala(); 
	Integer getLingkarlengan();
	Timestamp getTanggalcheckup();
	Timestamp getTanggalcheckupberikutnya(); 
	String getCatatancheckup();

}
