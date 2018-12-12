package com.amiablecore.warehouse.beans;

import org.springframework.stereotype.Component;

@Component
public class LoginResponse {
	private int loginIndicator;
	private String loggedInMessage;

	public int getLoginIndicator() {
		return loginIndicator;
	}

	public void setLoginIndicator(int loginIndicator) {
		this.loginIndicator = loginIndicator;
	}

	public String getLoggedInMessage() {
		return loggedInMessage;
	}

	public void setLoggedInMessage(String loggedInMessage) {
		this.loggedInMessage = loggedInMessage;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	private Customer customer;
}