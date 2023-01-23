package com.skirpsi.api.posyandu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="userposyandu")
//@JoinColumn(name = "secondary_address_id")
public class UserPosyandu {
	
	@Id
	@Column(name="iduser")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer idUser;
	
	@Column(name="namauser")
	private String namaUser;
	
	@Column(name="NIKUser")
	private String nik;
	
	@Column(name="tanggallahir")
	private Date TanggalLahir;
	
	@Column(name="passworduser")
	private String PasswordUser;
	
	@Column(name="noteleponuser")
	private String  NoTeleponUser;
	
	@Column(name="alamatuser")
	private String AlamatUser;
	
	@Column(name="usertype")
	private Integer userType;
	

}
