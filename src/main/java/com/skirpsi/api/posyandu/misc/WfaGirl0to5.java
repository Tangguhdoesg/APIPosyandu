package com.skirpsi.api.posyandu.misc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="wfagirl0to5")
public class WfaGirl0to5 {

	@Id
	@Column(name = "months")
	private Integer months;
	
	@Column(name = "neg3sd")
	private Float neg3sd;
	
	@Column(name = "neg2sd")
	private Float neg2sd;
	
	@Column(name = "neg1sd")
	private Float neg1sd;
	
	@Column(name = "median")
	private Float median;
	
	@Column(name = "pos1sd")
	private Float post1sd;
	
	@Column(name = "pos2sd")
	private Float post2sd;
	
	@Column(name = "pos3sd")
	private Float post3sd;
}
