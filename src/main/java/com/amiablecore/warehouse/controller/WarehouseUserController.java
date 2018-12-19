package com.amiablecore.warehouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.service.WarehouseUserService;

@RestController
public class WarehouseUserController {
	@Autowired
	private WarehouseUserService warehouseUserService;

	@GetMapping(value = "/category/retrieveCategories/{whAdminId}")
	public ResponseEntity<List<Category>> addCategory(@PathVariable("whAdminId") String whAdmiId) {
		List<Category> categories = warehouseUserService.retrieveCategories(whAdmiId);
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}
}
