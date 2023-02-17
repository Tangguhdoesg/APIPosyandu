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
@Table(name="checkup")
public class CheckUp {
	
	@Id
	@Column(name="idcheckup")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer IdCheckup;
	
	@OneToOne
	@JoinColumn(name = "idbalita")
	private Balita IdBalita;
	
	@Column(name="tinggibadan")
	private Float TinggiBadan;
	
	@Column(name="beratbadan")
	private Float BeratBadan;
	
	@Column(name="lingkarkepala")
	private Float LingkarKepala;
	
	@Column(name="lingkarlengan")
	private Float LingkarLengan;
	
	@Column(name="tanggalcheckup")
	private Timestamp TanggalCheckup;
	
	@Column(name="tanggalcheckupberikutnya")
	private Timestamp TanggalCheckupBerikutnya;
	
	@Column(name="catatancheckup")
	private String Catatan;
}
