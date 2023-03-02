package com.skirpsi.api.posyandu.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="imunisasi")
public class Imunisasi {
	@Id
	@Column(name="idimunisasi")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer idImunisasi;
	
	@OneToOne
	@JoinColumn(name = "idbalita")
	private Balita idBalita;
	
	@Column(name="namaimunisasi")
	private String namaImunisasi;
	
	@Column(name="tanggalimunisasi")
	private Timestamp tanggalImunisasi;
	
	@Column(name="tanggalimunisasiberikutnya")
	private Timestamp tanggalImunisasiBerikutnya;
	
	@Column(name="catatanimunisasi")
	private String catatan;
}
