package com.skirpsi.api.posyandu.misc;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CreateImunisasiEntity {
	private String namaImunisasi;
	private String catatanImunisasi;
	private Timestamp tanggalImunisasi;
	private Timestamp tanggalImunisasiBerikutnya;
	private String namaBalita;
	private String namaOrangTua;
	private String nikBalita;
}
