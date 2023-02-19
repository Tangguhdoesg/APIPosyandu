package com.skirpsi.api.posyandu.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="userposyandu")
public class UserPosyandu {
	
	@Id
	@Column(name="iduser")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer idUser;
	
	@Column(name="namauser")
	private String namaUser;
	
	@Column(name="NIKUser")
	private String nikUser;
	
	@Column(name="tanggallahir")
	private Date tanggalLahirUser;
	
	@Column(name="passworduser")
	private String passwordUser;
	
	@Column(name="noteleponuser")
	private String  noTeleponUser;
	
	@Column(name="alamatuser")
	private String alamatUser;
	
	@Column(name="usertype")
	private Integer userType;

}
