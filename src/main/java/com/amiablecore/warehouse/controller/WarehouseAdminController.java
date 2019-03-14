package com.amiablecore.warehouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Grade;
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
		if (newTrader.isAlreadyPresent()) {
			return new ResponseEntity<Trader>(newTrader, HttpStatus.OK);
		}
		return new ResponseEntity<Trader>(newTrader, HttpStatus.CREATED);
	}

	@PostMapping(value = "/commodity/add")
	public ResponseEntity<Commodity> addCommodity(@RequestBody Commodity commodity) {
		Commodity newCommodity = warehouseAdminService.addCommodity(commodity);
		return new ResponseEntity<Commodity>(newCommodity, HttpStatus.CREATED);
	}

	@PostMapping(value = "/category/add/{commodityId}")
	public ResponseEntity<Category> addCategory(@RequestBody Category category,
			@PathVariable("commodityId") String commodityId) {
		Category newcategory = warehouseAdminService.addCategory(category, commodityId);
		return new ResponseEntity<Category>(newcategory, HttpStatus.CREATED);
	}

	@GetMapping(value = "/commodity/retrieve/admin/{whAdminId}")
	public ResponseEntity<List<Commodity>> retrieveCommodities(@PathVariable("whAdminId") Integer whAdminId) {
		List<Commodity> commodityList = warehouseAdminService.retrieveCommodities(whAdminId);
		return new ResponseEntity<List<Commodity>>(commodityList, HttpStatus.OK);
	}

	@GetMapping(value = "/category/retrieve/admin/{commodityId}")
	public ResponseEntity<List<Category>> retrieveCategories(@PathVariable("commodityId") Integer commodityId) {
		List<Category> categories = warehouseAdminService.retrieveCategories(commodityId);
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}
	
	@PostMapping(value = "/grade/add/{commodityId}")
	public ResponseEntity<Grade> addGrade(@RequestBody Grade grade,
			@PathVariable("commodityId") String commodityId) {
		Grade newGrade = warehouseAdminService.addGrade(grade, commodityId);
		return new ResponseEntity<Grade>(newGrade, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/grade/retrieve/admin/{commodityId}")
	public ResponseEntity<List<Grade>> retrieveGrades(@PathVariable("commodityId") Integer commodityId) {
		List<Grade> grades = warehouseAdminService.retrieveGrades(commodityId);
		return new ResponseEntity<List<Grade>>(grades, HttpStatus.OK);
	}

}