package com.amiablecore.warehouse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Trader;
import com.amiablecore.warehouse.beans.WarehouseUser;
import com.amiablecore.warehouse.dao.WarehouseAdminDAO;
import com.amiablecore.warehouse.service.WarehouseAdminService;

/**
 * @author Pravin
 *
 */
@Service("warehouseAdminService")
public class WarehouseAdminServiceImpl implements WarehouseAdminService {

	@Autowired
	private WarehouseAdminDAO warehouseAdminDao;

	@Override
	public WarehouseUser createUser(WarehouseUser user) {
		return warehouseAdminDao.createUser(user);
	}

	@Override
	public Trader createTrader(Trader trader) {
		return warehouseAdminDao.createTrader(trader);
	}

	@Override
	public Commodity addCommodity(Commodity commodity) {
		return warehouseAdminDao.addCommodity(commodity);
	}

	@Override
	public Category addCategory(Category category) {
		return warehouseAdminDao.addCategory(category);
	}

	@Override
	public List<Commodity> retrieveCommodities(String whAdminId) {
		return warehouseAdminDao.retrieveCommodities(whAdminId);
	}
}
