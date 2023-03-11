package com.skirpsi.api.posyandu.payload.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
	private String notelepon;

	@NotBlank
	private String password;

	public String getnotelepon() {
		return notelepon;
	}

	public void setNoTelepon(String noTelepon) {
		this.notelepon = noTelepon;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
