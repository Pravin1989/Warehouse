package com.amiablecore.warehouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amiablecore.warehouse.beans.Customer;
import com.amiablecore.warehouse.dao.CustomerDAO;
import com.amiablecore.warehouse.service.CustomerService;

/**
 * @author Pravin
 *
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDao;

	@Override
	public Customer createCustomer(Customer customer) {
		return customerDao.createCustomer(customer);
	}

	@Override
	public String deleteCustomer(String customerId) {
		return customerDao.removeCustomer(customerId);
	}

	@Override
	public Customer getCustomer(String customerId) {
		return customerDao.retrieveCustomer(customerId);
	}

	@Override
	public String updateCustomer(Customer customer) {
		return customerDao.updateCustomer(customer);
	}
}
