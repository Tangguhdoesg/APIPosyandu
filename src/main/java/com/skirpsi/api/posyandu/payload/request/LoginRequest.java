package com.skirpsi.api.posyandu.payload.request;

public class LoginRequest {
	private String notelepon;

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
