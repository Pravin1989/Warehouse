package com.amiablecore.warehouse.dao;

import com.amiablecore.warehouse.beans.Customer;

/**
 * @author Pravin
 *
 */
public interface CustomerDAO {
	public Customer retrieveCustomer(String customerId);

	public Customer createCustomer(Customer customer);

	public String updateCustomer(Customer customer);

	public String removeCustomer(String customerId);
}