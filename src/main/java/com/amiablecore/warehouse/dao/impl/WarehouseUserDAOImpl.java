package com.amiablecore.warehouse.dao.impl;

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

	@Override
	public WarehouseUser createUser(WarehouseUser customer) {
		return null;
	}

}
