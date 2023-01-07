package com.skirpsi.api.posyandu.entity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="balita")
public class Balita {
	
	@Id
	@Column(name = "idbalita")
	private Integer idBalita;
	
	@OneToOne
	@JoinColumn(name = "iduser")
//	@Column(name="IdUser")
	private UserPosyandu user;
	
	@Column(name="namabalita")
	private String namabalita;
	
	@Column(name="tempatlahirbalita")
	private String tempatLahirBalita;
	
	@Column(name="tanggallahirbalita")
	private Timestamp tanggalLahirBalita;
	
	@Column(name="jeniskelaminbalita")
	private String jenisKelaminBalita;
	
	@Column(name="beratsaatlahir")
	private Float beratSaatLahir;
	
	@Column(name="tinggisaatlahir")
	private Float tinggiSaatLahir;
	
	@OneToMany
	@JoinColumn(name = "idcheckup")
//	@Column(name="IdUser")
	private List<CheckUp> checkup;
	
	@OneToMany
	@JoinColumn(name = "idimunisasi")
//	@Column(name="IdUser")
	private List<Imunisasi> imunisasi;
}
