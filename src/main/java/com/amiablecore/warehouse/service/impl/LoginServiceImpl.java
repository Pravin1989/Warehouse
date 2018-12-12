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

	@Override
	public LoginResponse validateLogin(LoginRequest request) {
		return loginDao.processLogin(request);
	}
}
