package com.skirpsi.api.posyandu.misc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="sfagirl0to5")
public class SfaGirl0to5 {
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
	
	@Column(name = "post1sd")
	private Float post1sd;
	
	@Column(name = "post2sd")
	private Float post2sd;
	
	@Column(name = "post3sd")
	private Float post3sd;
}
