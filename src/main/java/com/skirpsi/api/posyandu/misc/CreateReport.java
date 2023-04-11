package com.skirpsi.api.posyandu.misc;

import lombok.Data;

@Data
public class CreateReport {
	private String tanggalAwal;
	private String tanggalAkhir;
	private String email;
}
