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
	private Integer idBalita;
	
	@OneToOne
	@JoinColumn(name = "idorangtua")
	private UserPosyandu idUser;
	
	@Column(name="namabalita")
	private String namaBalita;
	
	@Column(name="nikbalita")
	private String nikBalita;
	
	@Column(name="tempatlahirbalita")
	private String tempatLahirBalita;
	
	@Column(name="tanggallahirbalita")
	private Timestamp tanggalLahirBalita;
	
	@Column(name="jeniskelaminbalita")
	private String jenisKelaminBalita;
	
	@Column(name="beratsaatlahirbalita")
	private Float beratSaatLahirBalita;
	
	@Column(name="tinggisaatlahirbalita")
	private Float tinggiSaatLahirBalita;
	
}
