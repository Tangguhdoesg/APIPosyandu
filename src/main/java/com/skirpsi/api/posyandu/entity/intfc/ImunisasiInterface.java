package com.skirpsi.api.posyandu.entity.intfc;

import java.sql.Timestamp;

public interface ImunisasiInterface {

	Integer getIdImunisasi();
	
	Integer getIdBalita();
	
	String getNamaImunisasi();
	
	Timestamp getTanggalImunisasi();
	
	Timestamp getTanggalImunisasiBerikutnya();
	
	String getCatatanImunisasi();
}
