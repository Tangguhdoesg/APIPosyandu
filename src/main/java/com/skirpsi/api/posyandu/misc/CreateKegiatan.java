package com.skirpsi.api.posyandu.misc;

import lombok.Data;

@Data
public class CreateKegiatan {
	private String namaKegiatan;
	private String tanggalKegiatan;
	private String lokasiKegiatan;
	private String nikPetugas;
}
