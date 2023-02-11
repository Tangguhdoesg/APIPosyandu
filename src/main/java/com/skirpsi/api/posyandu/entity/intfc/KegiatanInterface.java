package com.skirpsi.api.posyandu.entity.intfc;

import java.sql.Timestamp;

public interface KegiatanInterface {
	
	Integer getIdkegiatan();
	String getNamakegiatan(); 
	Timestamp getTanggalkegiatan(); 
	String getLokasikegiatan();
	byte[] getPosterkegiatan();
	String getNamaposter();

}
