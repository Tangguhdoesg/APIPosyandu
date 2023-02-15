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
@Table(name="kegiatan")
public class Kegiatan {
	
	@Id
	@Column(name="idkegiatan")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer IdKegiatan;
	
	@Column(name="namakegiatan")
	private String NamaKegiatan;
	
	@Column(name="tanggalkegiatan")
	private Timestamp TanggalKegiatan;
	
	@Column(name="lokasikegiatan")
	private String LokasiKegiatan;
	
	@OneToOne
	@JoinColumn(name = "iduser")
	private UserPosyandu PenanggungJawabKegiatan;
	
	@Column(name="posterkegiatan")
	private byte[] PosterKegiatan;
	
	@Column(name="namaposter")
	private String NamaPosterKegiatan;

}
