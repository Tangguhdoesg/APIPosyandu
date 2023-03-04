package com.skirpsi.api.posyandu.entity.intfc;

import java.sql.Timestamp;

public interface BalitaInterface {
	
	Integer getIdOrangTua();
	
	String getIdBalita();
	
	String  getNamaBalita();
	
	String getNikBalita();
	
	String getTempatLahirBalita();
	
	Timestamp getTanggalLahirBalita();
	
	String getJenisKelaminBalita();
	
	Float getBeratSaatLahirBalita();

	Float getTinggiSaatLahirBalita();
}
