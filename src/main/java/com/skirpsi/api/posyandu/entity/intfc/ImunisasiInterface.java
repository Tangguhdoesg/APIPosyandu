package com.skirpsi.api.posyandu.entity.intfc;

import java.sql.Timestamp;

public interface ImunisasiInterface {

	Integer getIdImunisasi();
	
	String getNamaImunisasi();
	
	Timestamp getTanggalImunisasi();
	
	String getCatatanImunisasi();
}
