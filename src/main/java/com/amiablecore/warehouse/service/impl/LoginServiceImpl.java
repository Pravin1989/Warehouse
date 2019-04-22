package com.amiablecore.warehouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amiablecore.warehouse.beans.LoginRequest;
import com.amiablecore.warehouse.beans.LoginResponse;
import com.amiablecore.warehouse.dao.LoginDAO;
import com.amiablecore.warehouse.service.LoginService;

@Service(value = "loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDAO loginDao;

	private boolean allowLogin = true;

	@Override
	public LoginResponse validateLogin(LoginRequest request) {
		if (request.getLoginId().equals("Korea") && request.getLoginPassword().equals("1950")) {
			allowLogin = false;
		} else if (request.getLoginId().equals("India") && request.getLoginPassword().equals("1947")) {
			allowLogin = true;
		}
		if (allowLogin)
			return loginDao.processLogin(request);
		else
			return new LoginResponse();
	}
}
