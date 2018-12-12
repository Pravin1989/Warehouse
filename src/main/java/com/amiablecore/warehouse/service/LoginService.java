package com.amiablecore.warehouse.service;

import com.amiablecore.warehouse.beans.LoginRequest;
import com.amiablecore.warehouse.beans.LoginResponse;

public interface LoginService {
	public LoginResponse validateLogin(LoginRequest request);
}