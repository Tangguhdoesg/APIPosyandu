package com.skirpsi.api.posyandu.entity.intfc;

import java.util.Date;

public interface ReportInterface {
	
	Integer getIdBalita();
	String getNamaBalita();
	String getNikbalita();
	Float getBeratSaatLahirBalita();
	Float getTinggiSaatLahirBalita();
	String getAlamatUser();
	String getNamaUser();
	Date getTanggalCheckup();
	Float getBeratBadan();
	Float getTinggiBadan();
	String getNamaImunisasi();
	Date getTanggalImunisasi();
	
}
