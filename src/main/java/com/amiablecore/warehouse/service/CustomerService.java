package com.amiablecore.warehouse.service;

import com.amiablecore.warehouse.beans.Customer;

/**
 * @author Pravin
 *
 */
public interface CustomerService {
	public Customer getCustomer(String customerId);

	public Customer createCustomer(Customer customer);

	public String updateCustomer(Customer customer);

	public String deleteCustomer(String customerId);
}
