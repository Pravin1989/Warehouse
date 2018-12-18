package com.amiablecore.warehouse.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Trader;
import com.amiablecore.warehouse.beans.WarehouseUser;
import com.amiablecore.warehouse.service.WarehouseAdminService;

@RestController
public class WarehouseAdminController {

	@Autowired
	private WarehouseAdminService warehouseAdminService;

	@PostMapping(value = "/user/create")
	public ResponseEntity<WarehouseUser> createCustomer(@RequestBody WarehouseUser user) {
		WarehouseUser newUser = warehouseAdminService.createUser(user);
		return new ResponseEntity<WarehouseUser>(newUser, HttpStatus.CREATED);
	}

	@PostMapping(value = "/trader/create")
	public ResponseEntity<Trader> createTrader(@RequestBody Trader trader) {
		Trader newTrader = warehouseAdminService.createTrader(trader);
		return new ResponseEntity<Trader>(newTrader, HttpStatus.CREATED);
	}

	@PostMapping(value = "/commodity/add")
	public ResponseEntity<Commodity> addCommodity(@RequestBody Commodity commodity) {
		Commodity newTrader = warehouseAdminService.addCommodity(commodity);
		return new ResponseEntity<Commodity>(newTrader, HttpStatus.CREATED);
	}

	@PostMapping(value = "/category/add")
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		Category newTrader = warehouseAdminService.addCategory(category);
		return new ResponseEntity<Category>(newTrader, HttpStatus.CREATED);
	}

	@GetMapping(value = "/commodity/retrieveCommodities/{whAdminId}")
	public ResponseEntity<List<Commodity>> addCategory(@PathVariable("whAdminId") String whAdmiId) {
		List<Commodity> commodities = warehouseAdminService.retrieveWhAminId(whAdmiId);
		return new ResponseEntity<List<Commodity>>(commodities, HttpStatus.OK);
	}
}