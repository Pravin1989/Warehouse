package com.amiablecore.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amiablecore.warehouse.beans.LoginRequest;
import com.amiablecore.warehouse.beans.LoginResponse;
import com.amiablecore.warehouse.beans.UserType;
import com.amiablecore.warehouse.service.LoginService;


@RestController
public class LoginController {

	@Autowired
	protected LoginService loginService;

	@PostMapping(value = "/login/{userType}")
	public ResponseEntity<LoginResponse> login(@PathVariable String type, @RequestBody LoginRequest request) {
		request.setUserType(UserType.fromString(type));
		LoginResponse response = loginService.validateLogin(request);
		return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
	}
}