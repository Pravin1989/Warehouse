package com.amiablecore.warehouse.dao;

import com.amiablecore.warehouse.beans.LoginRequest;
import com.amiablecore.warehouse.beans.LoginResponse;

public interface LoginDAO {
	public LoginResponse processLogin(LoginRequest request);
}