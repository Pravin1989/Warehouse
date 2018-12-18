package com.amiablecore.warehouse.beans;

import org.springframework.stereotype.Component;

@Component
public class LoginResponse {
	private boolean loginIndicator;
	private String loggedInMessage;
	private String whId;
	
	public String getWhId() {
		return whId;
	}

	public void setWhId(String whId) {
		this.whId = whId;
	}

	public boolean getLoginIndicator() {
		return loginIndicator;
	}

	public void setLoginIndicator(boolean loginIndicator) {
		this.loginIndicator = loginIndicator;
	}

	public String getLoggedInMessage() {
		return loggedInMessage;
	}

	public void setLoggedInMessage(String loggedInMessage) {
		this.loggedInMessage = loggedInMessage;
	}
}