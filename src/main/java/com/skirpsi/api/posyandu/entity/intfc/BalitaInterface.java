package com.skirpsi.api.posyandu.entity.intfc;

import java.sql.Timestamp;

public interface BalitaInterface {
	String getIdBalita();
	
	String  getNamabalita();
	
	String getTempatLahirBalita();
	
	Timestamp getTanggalLahirBalita();
	
	String getJenisKelaminBalita();
	
	Float getBeratSaatLahir();

	Float getTinggiSaatLahir();
}
