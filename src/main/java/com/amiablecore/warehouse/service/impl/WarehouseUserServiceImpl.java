package com.amiablecore.warehouse.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static Logger logger = LoggerFactory.getLogger(WarehouseUserServiceImpl.class);

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
	public Inward retrieveLotDetails(Integer lotId) {
		return warehouseUserDAO.retrieveLotDetails(lotId);
	}

	@Override
	public List<Inward> retrieveLotList(String lotName) {
		return warehouseUserDAO.retrieveLotList(lotName);
	}

	@Override
	public Inward storeInwardDetails(Inward inward) {
		logger.info("Inward Lot Service :");
		return warehouseUserDAO.storeInwardDetails(inward);
	}

	@Override
	public Outward storeOutwardDetails(Outward outward) {
		return warehouseUserDAO.storeOutwardDetails(outward);
	}

	@Override
	public List<Inward> retrieveInCompleteInward(Integer whUserId) {
		return warehouseUserDAO.retrieveInCompleteInward(whUserId);
	}

	@Override
	public List<Outward> retrieveInCompleteOutward(Integer whUserId) {
		return warehouseUserDAO.retrieveInCompleteOutward(whUserId);
	}

	@Override
	public String updateTotalWeightInward(Integer inwardId, Double totalWeight) {
		return warehouseUserDAO.updateTotalWeightInward(inwardId, totalWeight);
	}

	@Override
	public String updateTotalWeightOutward(Outward outward) {
		return warehouseUserDAO.updateTotalWeightOutward(outward);
	}

}
