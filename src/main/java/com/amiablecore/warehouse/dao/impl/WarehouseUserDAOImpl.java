package com.amiablecore.warehouse.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Trader;
import com.amiablecore.warehouse.dao.WarehouseUserDAO;

@Repository("warehouseUserDAO")
public class WarehouseUserDAOImpl implements WarehouseUserDAO {

	private static Logger logger = LoggerFactory.getLogger(WarehouseUserDAOImpl.class);

	@Override
	public List<Category> retrieveCategories(String whAdminId) {
		List<Category> list = new ArrayList<Category>();
		for (Map.Entry<Integer, Category> category : WarehouseAdminDAOImpl.categories.entrySet()) {
			if (category.getValue().getWhAdminId().equals(whAdminId)) {
				list.add(category.getValue());
			}
		}
		logger.info("Categories : {}", list);
		return list;
	}

	@Override
	public List<Trader> retrieveTraders(String whAdminId) {
		List<Trader> list = new ArrayList<Trader>();
		for (Map.Entry<Integer, Trader> trader : WarehouseAdminDAOImpl.traders.entrySet()) {
			if (trader.getValue().getWhAdminId().equals(whAdminId)) {
				list.add(trader.getValue());
				logger.info("Trader ");
			}
		}
		logger.info("Traders  : {}", list);
		return list;
	}
	
	@Override
	public List<Commodity> retrieveCommodities(String whAdminId) {
		List<Commodity> commoditiesList = new ArrayList<>();
		if (Integer.parseInt(whAdminId) == 1000) {
			for (Map.Entry<Integer, Commodity> commodity : WarehouseAdminDAOImpl.commodities.entrySet()) {
				Commodity com = new Commodity();
				com.setCommodityId(commodity.getValue().getCommodityId());
				com.setCommodityName(commodity.getValue().getCommodityName());
				com.setWhAdminId(commodity.getValue().getWhAdminId());
				commoditiesList.add(com);
			}
		}
		return commoditiesList;
	}

}