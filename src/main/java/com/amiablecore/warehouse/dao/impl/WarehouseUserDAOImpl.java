package com.amiablecore.warehouse.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.amiablecore.warehouse.beans.WarehouseUser;
import com.amiablecore.warehouse.dao.WarehouseUserDAO;

/**
 * @author Pravin
 *
 */
@Repository("warehouseUserDao")
public class WarehouseUserDAOImpl implements WarehouseUserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Value(value = "${tablePrefix}")
	private String tablePrefix;

	private static Logger logger = LoggerFactory.getLogger(WarehouseUserDAOImpl.class);
	
	@Override
	public WarehouseUser createUser(WarehouseUser user) {
		logger.info("LoginId : {}",user.getLoginId());
		logger.info("Name : {}",user.getName());
		logger.info("Password  : {}",user.getPassword());
		logger.info("Contact No : : {}",user.getContactNo());
		logger.info("WH ID : {}",user.getWhId());
		return user;
	}

}
