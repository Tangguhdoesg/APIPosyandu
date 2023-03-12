package com.skirpsi.api.posyandu.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(	name="userauthenticate",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "username")
			}
		)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Size(max = 20)
	private String username;
	@NotBlank
	@Size(max = 120)
	private String password;
	
	@Column(name="iduser")
	private Integer iduser;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User(String username, String password,Integer iduser) {
		this.username = username;
	    this.password = password;
	    this.iduser = iduser;
	}

	public User(Long id, @NotBlank @Size(max = 20) String username,
			@NotBlank @Size(max = 120) String password, Set<Role> roles,Integer iduser) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.iduser = iduser;
	}

	public User() {
		super();
	}
	
	
	
	
	
}
