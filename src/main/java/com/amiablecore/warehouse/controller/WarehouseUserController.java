package com.amiablecore.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amiablecore.warehouse.beans.WarehouseUser;
import com.amiablecore.warehouse.service.WarehouseUserService;

@RestController
public class WarehouseUserController {

	@Autowired
	private WarehouseUserService warehouseUserService;
					
	@PostMapping(value = "/user/create")
	public ResponseEntity<WarehouseUser> createCustomer(@RequestBody WarehouseUser user) {
		WarehouseUser newCustomer = warehouseUserService.createUser(user);
		return new ResponseEntity<WarehouseUser>(newCustomer, HttpStatus.CREATED);
	}
}