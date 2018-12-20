package com.amiablecore.warehouse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Trader;
import com.amiablecore.warehouse.dao.WarehouseUserDAO;
import com.amiablecore.warehouse.service.WarehouseUserService;

@Service("warehouseUserService")
public class WarehouseUserServiceImpl implements WarehouseUserService {

	@Autowired
	private WarehouseUserDAO warehouseUserDAO;

	@Override
	public List<Category> retrieveCategories(String whAdminId) {
		return warehouseUserDAO.retrieveCategories(whAdminId);
	}

	@Override
	public List<Trader> retrieveTraders(String whAdminId) {
		return warehouseUserDAO.retrieveTraders(whAdminId);
	}
	
	@Override
	public List<Commodity> retrieveCommodities(String whAdminId) {
		return warehouseUserDAO.retrieveCommodities(whAdminId);
	}
}
