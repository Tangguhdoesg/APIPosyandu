package com.skirpsi.api.posyandu.entity.intfc;

import java.util.Date;

public interface ReportInterface {
	
	Integer getIdBalita();
	String getNamaBalita();
	String getNikbalita();
	Float getBeratSaatLahir();
	Float getTinggiSaatLahir();
	String getAlamatUser();
	String getNamaUser();
	Date getTanggalCheckup();
	Float getBeratBadan();
	Float getTinggiBadan();
}
