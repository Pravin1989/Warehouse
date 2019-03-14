package com.amiablecore.warehouse.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Email;
import com.amiablecore.warehouse.beans.Inward;
import com.amiablecore.warehouse.beans.Outward;
import com.amiablecore.warehouse.beans.Trader;
import com.amiablecore.warehouse.service.WarehouseUserService;

@RestController
public class WarehouseUserController {
	@Autowired
	private WarehouseUserService warehouseUserService;

	@GetMapping(value = "/category/retrieveCategories/{commodityId}")
	public ResponseEntity<List<Category>> retrieveCategories(@PathVariable("commodityId") Integer commodityId) {
		List<Category> categories = warehouseUserService.retrieveCategories(commodityId);
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}

	@GetMapping(value = "/trader/retrieveTraders/{traderName}")
	public ResponseEntity<List<Trader>> retrieveTraderDetails(@PathVariable("traderName") String traderName) {
		List<Trader> traders = warehouseUserService.retrieveTraders(traderName);
		return new ResponseEntity<List<Trader>>(traders, HttpStatus.OK);
	}

	@GetMapping(value = "/commodity/retrieveCommodities/{whAdminId}")
	public ResponseEntity<List<Commodity>> retrieveCommodities(@PathVariable("whAdminId") Integer whAdminId) {
		List<Commodity> commodities = warehouseUserService.retrieveCommodities(whAdminId);
		return new ResponseEntity<List<Commodity>>(commodities, HttpStatus.OK);
	}

	@GetMapping(value = "/lots/retrieveLotList/{lotName}")
	public ResponseEntity<List<Inward>> retrieveLotList(@PathVariable("lotName") String lotName) {
		List<Inward> lotList = warehouseUserService.retrieveLotList(lotName);
		return new ResponseEntity<List<Inward>>(lotList, HttpStatus.OK);
	}

	@GetMapping(value = "/lots/retrieveLotDetails/{lotId}")
	public ResponseEntity<Inward> retrieveLotDetails(@PathVariable("lotId") Integer lotId) {
		Inward inward = warehouseUserService.retrieveLotDetails(lotId);
		return new ResponseEntity<Inward>(inward, HttpStatus.OK);
	}

	@PostMapping(value = "/lot/inward/")
	public ResponseEntity<Inward> storeInwardDetails(@RequestBody Inward inward) {
		Inward storeInwardDetails = warehouseUserService.storeInwardDetails(inward);
		return new ResponseEntity<Inward>(storeInwardDetails, HttpStatus.CREATED);
	}

	@PostMapping(value = "/lot/outward/")
	public ResponseEntity<String> storeOutwardDetails(@RequestBody Outward outward) {
		warehouseUserService.storeOutwardDetails(outward);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@GetMapping(value = "/lot/inward/incomplete/{whUserId}")
	public ResponseEntity<List<Inward>> retrieveInCompleteInward(@PathVariable("whUserId") Integer whUserId) {
		List<Inward> inwardList = warehouseUserService.retrieveInCompleteInward(whUserId);
		return new ResponseEntity<List<Inward>>(inwardList, HttpStatus.OK);
	}

	@GetMapping(value = "/lot/outward/incomplete/{whUserId}")
	public ResponseEntity<List<Outward>> retrieveInCompleteOutward(@PathVariable("whUserId") Integer whUserId) {
		List<Outward> outwardList = warehouseUserService.retrieveInCompleteOutward(whUserId);
		return new ResponseEntity<List<Outward>>(outwardList, HttpStatus.OK);
	}

	@GetMapping(value = "/lot/inward/update/{inwardId}/weight/{totalWeight}")
	public ResponseEntity<String> updateTotalWeightInward(@PathVariable("inwardId") Integer inwardId,
			@PathVariable("totalWeight") String totalWeight) {
		warehouseUserService.updateTotalWeightInward(inwardId, Double.parseDouble(totalWeight));
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@PostMapping(value = "/lot/outward/update/")
	public ResponseEntity<String> updateTotalWeightOutward(@RequestBody Outward outward) {
		warehouseUserService.updateTotalWeightOutward(outward);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@GetMapping(value = "/retrieve/units/")
	public ResponseEntity<List<String>> retrieveUnits() {
		List<String> units = warehouseUserService.retrieveUnits();
		return new ResponseEntity<List<String>>(units, HttpStatus.OK);
	}

	@GetMapping(value = "/retrieve/grades/")
	public ResponseEntity<List<String>> retrieveGrades() {
		List<String> grades = warehouseUserService.retrieveGrades();
		return new ResponseEntity<List<String>>(grades, HttpStatus.OK);
	}

	@PostMapping(value = "/email/")
	public ResponseEntity<Boolean> sendEmail(@RequestBody Email email) {
		warehouseUserService.sendEmail(email);
		return new ResponseEntity<Boolean>(HttpStatus.OK);
	}

}
