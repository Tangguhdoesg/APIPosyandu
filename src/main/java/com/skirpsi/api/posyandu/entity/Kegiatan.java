package com.skirpsi.api.posyandu.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="kegiatan")
public class Kegiatan {
	
	@Id
	@Column(name="idkegiatan")
	private Integer idKegiatan;
	
	@Column(name="namakegiatan")
	private String namaKegiatan;
	
	@Column(name="tanggalkegiatan")
	private Timestamp anggalKegiatan;
	
	@Column(name="lokasikegiatan")
	private String lokasiKegiatan;
	
	@OneToOne
	@JoinColumn(name = "idpenanggungjawab")
//	@Column(name="IdPenanggungJawab")
	private UserPosyandu idPenanggungJawab;

}
