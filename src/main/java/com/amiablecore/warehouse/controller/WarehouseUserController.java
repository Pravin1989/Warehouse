package com.amiablecore.warehouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Trader;
import com.amiablecore.warehouse.service.WarehouseUserService;

@RestController
public class WarehouseUserController {
	@Autowired
	private WarehouseUserService warehouseUserService;

	@GetMapping(value = "/category/retrieveCategories/{commodityId}")
	public ResponseEntity<List<Category>> retrieveCategories(@PathVariable("commodityId") String commodityId) {
		List<Category> categories = warehouseUserService.retrieveCategories(commodityId);
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}

	@GetMapping(value = "/trader/retrieveTraders/{whAdminId}")
	public ResponseEntity<List<Trader>> retrieveTraderDetails(@PathVariable("whAdminId") String whAdmiId) {
		List<Trader> categories = warehouseUserService.retrieveTraders(whAdmiId);
		return new ResponseEntity<List<Trader>>(categories, HttpStatus.OK);
	}
	

	@GetMapping(value = "/commodity/retrieveCommodities/{whAdminId}")
	public ResponseEntity<List<Commodity>> retrieveCommodities(@PathVariable("whAdminId") String whAdmiId) {
		List<Commodity> commodities = warehouseUserService.retrieveCommodities(whAdmiId);
		return new ResponseEntity<List<Commodity>>(commodities, HttpStatus.OK);
	}
}
