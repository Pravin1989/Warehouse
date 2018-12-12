package com.amiablecore.warehouse.beans;

import org.springframework.stereotype.Component;

@Component
public class LoginRequest {

	private String loginId;
	private String loginPassword;
	private UserType userType;

	public String getLoginId() {
		return loginId;
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

}
