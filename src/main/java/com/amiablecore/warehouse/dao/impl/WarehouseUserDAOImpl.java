package com.amiablecore.warehouse.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Inward;
import com.amiablecore.warehouse.beans.Outward;
import com.amiablecore.warehouse.beans.Trader;
import com.amiablecore.warehouse.dao.WarehouseUserDAO;

@Repository("warehouseUserDAO")
public class WarehouseUserDAOImpl implements WarehouseUserDAO {

	private static Logger logger = LoggerFactory.getLogger(WarehouseUserDAOImpl.class);

	@Override
	public List<Category> retrieveCategories(String commodityId) {
		// Need to retrieve cateryId and its name
		List<Category> list = new ArrayList<Category>();
		for (Map.Entry<Integer, List<Category>> commodity : WarehouseAdminDAOImpl.commodityCategories.entrySet()) {
			if (commodity.getKey() == Integer.parseInt(commodityId)) {
				logger.info("Categories : {}", commodity.getValue());
				return commodity.getValue();
			}
		}
		return list;
	}

	@Override
	public List<Trader> retrieveTraders(String traderName) {
		List<Trader> dblist = new ArrayList<Trader>();
		List<Trader> list = new ArrayList<Trader>();
		// for (Map.Entry<Integer, Trader> trader :
		// WarehouseAdminDAOImpl.traders.entrySet()) {
		// if (trader.getValue().getWhAdminId().equals(whAdminId)) {
		// list.add(trader.getValue());
		// logger.info("Trader ");
		// }
		// }

		Trader tr1 = new Trader("Pravin", "", 111);
		Trader tr2 = new Trader("Pranav", "", 112);
		Trader tr3 = new Trader("Dhananjay", "", 113);
		Trader tr4 = new Trader("Dj", "", 114);

		dblist.add(tr1);
		dblist.add(tr2);
		dblist.add(tr3);
		dblist.add(tr4);
		logger.info("Traders  : {}", dblist);
		for (int i = 0; i < dblist.size(); i++) {
			Trader t = dblist.get(i);
			if (t.getTraderName().contains(traderName)) {
				list.add(t);
			}
		}
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

	@Override
	public Inward retrieveLotDetails(String lotId) {
		return null;
	}

	@Override
	public List<Inward> retrieveLotList(String lotName) {
		return null;
	}

	@Override
	public String synchronizeInward(List<Inward> inwardList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String synchronizeOutward(List<Outward> outwardList) {
		// TODO Auto-generated method stub
		return null;
	}
}