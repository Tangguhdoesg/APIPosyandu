package com.skirpsi.api.posyandu.misc;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@Data
public class CreateKegiatan {
	private String namaKegiatan;
	private String tanggalKegiatan;
	private String lokasiKegiatan;
	private String nikPetugas;
}
