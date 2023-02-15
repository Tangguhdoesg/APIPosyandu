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
@Table(name="balita")
public class Balita {
	
	@Id
	@Column(name = "idbalita")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer IdBalita;
	
	@OneToOne
	@JoinColumn(name = "idorangtua")
	private UserPosyandu IdUser;
	
	@Column(name="namabalita")
	private String NamaBalita;
	
	@Column(name="nikbalita")
	private String NIKBalita;
	
	@Column(name="tempatlahirbalita")
	private String TempatLahirBalita;
	
	@Column(name="tanggallahirbalita")
	private Timestamp TanggalLahirBalita;
	
	@Column(name="jeniskelaminbalita")
	private String JenisKelaminBalita;
	
	@Column(name="beratsaatlahir")
	private Float BeratSaatLahirBalita;
	
	@Column(name="tinggisaatlahir")
	private Float TinggiSaatLahirBalita;
	
}
