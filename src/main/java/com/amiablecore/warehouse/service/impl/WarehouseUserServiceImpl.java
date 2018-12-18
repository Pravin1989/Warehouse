package com.amiablecore.warehouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amiablecore.warehouse.beans.WarehouseUser;
import com.amiablecore.warehouse.dao.WarehouseUserDAO;
import com.amiablecore.warehouse.service.WarehouseUserService;

/**
 * @author Pravin
 *
 */
@Service("warehouseUser")
public class WarehouseUserServiceImpl implements WarehouseUserService {

	@Autowired
	private WarehouseUserDAO warehouseDao;

	@Override
	public WarehouseUser createUser(WarehouseUser user) {
		return warehouseDao.createUser(user);
	}
}
