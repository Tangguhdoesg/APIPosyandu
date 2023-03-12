package com.skirpsi.api.posyandu.payload.request;

import lombok.Data;

@Data
public class LoginRequest {
	private String notelepon;

	private String password;
}
