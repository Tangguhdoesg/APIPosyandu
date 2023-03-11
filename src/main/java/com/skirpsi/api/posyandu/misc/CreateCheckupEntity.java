package com.skirpsi.api.posyandu.misc;

import java.sql.Timestamp;
import com.skirpsi.api.posyandu.entity.Balita;

import lombok.Data;

@Data
public class CreateCheckupEntity {
	
	private Integer idBalita;
	
	private Float tinggiBadan;
	
	private Float beratBadan;
	
	private Float lingkarKepala;
	
	private Float lingkarLengan;
	
	private Timestamp tanggalCheckup;
	
	private Timestamp tanggalCheckupBerikutnya;
	
	private String catatan;
	
	private String nikBalita;
}
