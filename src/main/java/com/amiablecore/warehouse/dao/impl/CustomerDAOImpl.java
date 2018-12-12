package com.amiablecore.warehouse.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.amiablecore.warehouse.beans.Customer;
import com.amiablecore.warehouse.dao.CustomerDAO;

/**
 * @author Pravin
 *
 */
@Repository("customerDao")
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Value(value = "${tablePrefix}")
	private String tablePrefix;

	@Override
	public Customer createCustomer(Customer customer) {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO " + tablePrefix);
		query.append(" table_customer(customer_id,customer_name,customer_address,gender,password,email_id,login_id)");
		query.append("VALUES(?,?,?,?,?,?,?)");
		Object arguments[] = { customer.getCustomerId(), customer.getCustomerName(), customer.getAddress(),
				customer.getGender(), " ", customer.getEmailId(), " " };
		jdbcTemplate.queryForList(query.toString(), arguments);
		return null;
	}

	@Override
	public String removeCustomer(String customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer retrieveCustomer(String customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}
}
