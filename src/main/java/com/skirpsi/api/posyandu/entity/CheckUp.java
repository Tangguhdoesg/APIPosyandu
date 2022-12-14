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
	private Integer idCheckup;
	
	@OneToOne
	@JoinColumn(name = "idbalita")
//	@Column(name="IdBalita")
	private Balita balita;
	
	@Column(name="tinggibadan")
	private Float tinggibadan;
	
	@Column(name="beratbadan")
	private Float beratBadan;
	
	@Column(name="lingkarkepala")
	private Float lingkarKepala;
	
	@Column(name="lingkarlengan")
	private Float lingkarLengan;
	
	@Column(name="tanggalcheckup")
	private Timestamp tanggalCheckup;
	
	@Column(name="tanggalcheckupberikutnya")
	private Timestamp tanggalCheckupBerikutnya;
}
