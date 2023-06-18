package com.user_management.api.dto;

import java.io.Serializable;
import java.util.UUID;

@SuppressWarnings("serial")
public class Token implements Serializable {

	private String token;

	public Token() {

	}

	public Token(UUID uuid) {
		this.token = uuid.toString();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
