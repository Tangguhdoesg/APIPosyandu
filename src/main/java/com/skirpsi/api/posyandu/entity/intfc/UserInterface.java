package com.skirpsi.api.posyandu.entity.intfc;

import java.sql.Timestamp;

public interface UserInterface {
	
	Integer getIduser();
	String getNamauser();
	String getNikuser();
	Timestamp getTanggallahir();
	String getNoteleponuser();
	String getAlamatuser();
	Integer getUsertype();
}
