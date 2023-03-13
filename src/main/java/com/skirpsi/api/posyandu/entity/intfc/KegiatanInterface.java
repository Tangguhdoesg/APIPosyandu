package com.skirpsi.api.posyandu.entity.intfc;

import java.sql.Timestamp;

public interface KegiatanInterface {
	
	Integer getIdKegiatan();
	String getNamaKegiatan(); 
	Timestamp getTanggalKegiatan(); 
	String getLokasiKegiatan();
	byte[] getPosterKegiatan();
	String getNamaPosterKegiatan();
	Integer getIdUser();

}
