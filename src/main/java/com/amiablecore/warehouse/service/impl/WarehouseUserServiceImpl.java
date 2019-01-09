package com.amiablecore.warehouse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Inward;
import com.amiablecore.warehouse.beans.Outward;
import com.amiablecore.warehouse.beans.Trader;
import com.amiablecore.warehouse.dao.WarehouseUserDAO;
import com.amiablecore.warehouse.service.WarehouseUserService;

@Service("warehouseUserService")
public class WarehouseUserServiceImpl implements WarehouseUserService {

	@Autowired
	private WarehouseUserDAO warehouseUserDAO;

	@Override
	public List<Category> retrieveCategories(Integer commodityId) {
		return warehouseUserDAO.retrieveCategories(commodityId);
	}

	@Override
	public List<Trader> retrieveTraders(String traderName) {
		return warehouseUserDAO.retrieveTraders(traderName);
	}

	@Override
	public List<Commodity> retrieveCommodities(Integer whAdminId) {
		return warehouseUserDAO.retrieveCommodities(whAdminId);
	}

	@Override
	public Inward retrieveLotDetails(String lotId) {
		return warehouseUserDAO.retrieveLotDetails(lotId);
	}

	@Override
	public List<Inward> retrieveLotList(String lotName) {
		return warehouseUserDAO.retrieveLotList(lotName);
	}

	@Override
	public String synchronizeInward(List<Inward> inwardList) {
		return warehouseUserDAO.synchronizeInward(inwardList);
	}

	@Override
	public String synchronizeOutward(List<Outward> outwardList) {
		return warehouseUserDAO.synchronizeOutward(outwardList);
	}
}
