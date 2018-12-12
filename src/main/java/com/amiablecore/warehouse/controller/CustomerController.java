package com.amiablecore.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amiablecore.warehouse.beans.Customer;
import com.amiablecore.warehouse.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping(value = "/customer/getCustomer/{customerId}")
	public ResponseEntity<Customer> getCustomer(@PathVariable String customerId) {
		Customer customer = customerService.getCustomer(customerId);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@PostMapping(value = "/customer/create")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		Customer newCustomer = customerService.createCustomer(customer);
		return new ResponseEntity<Customer>(newCustomer, HttpStatus.CREATED);
	}

	@PutMapping(value = "/customer/update/{customerId}")
	public ResponseEntity<String> updateCustomer(@RequestBody Customer customer) {
		String updateResult = customerService.updateCustomer(customer);
		return new ResponseEntity<String>(updateResult, HttpStatus.OK);
	}

	@DeleteMapping(value = "/customer/delete/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable String customerId) {
		String deleteResult = customerService.deleteCustomer(customerId);
		return new ResponseEntity<String>(deleteResult, HttpStatus.NO_CONTENT);
	}

}