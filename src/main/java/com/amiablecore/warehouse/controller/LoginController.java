package com.amiablecore.warehouse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@PostMapping(value = "/login/{userType}")
	public ResponseEntity<LoginResponse> login(@PathVariable("userType") String type,
			@RequestBody LoginRequest request) {
		request.setUserType(UserType.fromString(type));
		LoginResponse response = loginService.validateLogin(request);
		if (response.getLoginIndicator()) {
			logger.info("User Found");
			return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
		}
		logger.info("User Not Found");
		return new ResponseEntity<LoginResponse>(response, HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/test")
	public ResponseEntity<String> testServerStatus() {
		return new ResponseEntity<String>("Server Is Running", HttpStatus.OK);
	}
}