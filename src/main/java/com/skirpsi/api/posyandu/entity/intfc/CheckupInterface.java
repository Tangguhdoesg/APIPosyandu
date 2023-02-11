package com.skirpsi.api.posyandu.entity.intfc;

import java.sql.Timestamp;

public interface CheckupInterface {
	
	Integer getIdcheckup();
	Integer getTinggibadan(); 
	Integer getBeratbadan(); 
	Integer getLingkarkepala(); 
	Integer getLingkarlengan();
	Timestamp getTanggalcheckup();
	Timestamp getTanggalcheckupberikutnya(); 
	String getCatatancheckup();

}
