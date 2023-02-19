package com.skirpsi.api.posyandu.entity.intfc;

import java.sql.Timestamp;

public interface UserInterface {
	
	Integer getIdUser();
	String getNamaUser();
	String getNikUser();
	Timestamp getTanggalLahirUser();
	String getNoTeleponUser();
	String getAlamatUser();
	Integer getUserType();
}
