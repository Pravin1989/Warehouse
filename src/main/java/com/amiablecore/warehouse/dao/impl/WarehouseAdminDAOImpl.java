package com.amiablecore.warehouse.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Trader;
import com.amiablecore.warehouse.beans.WarehouseUser;
import com.amiablecore.warehouse.dao.WarehouseAdminDAO;

/**
 * @author Pravin
 *
 */
@Repository("warehouseAdminDao")
public class WarehouseAdminDAOImpl implements WarehouseAdminDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Value(value = "${tablePrefix}")
	private String tablePrefix;

	private static Logger logger = LoggerFactory.getLogger(WarehouseAdminDAOImpl.class);

	private static Map<Integer, Commodity> commodities = new HashMap<>();

	private Integer commodityCounter = 100;

	private Set<Integer> keySet;

	@Override
	public WarehouseUser createUser(WarehouseUser user) {
		logger.info("LoginId : {}", user.getLoginId());
		logger.info("Name : {}", user.getName());
		logger.info("Password  : {}", user.getPassword());
		logger.info("Contact No :  {}", user.getContactNo());
		logger.info("WH ID : {}", user.getWhId());
		return user;
	}

	@Override
	public Trader createTrader(Trader trader) {
		logger.info("Trader Name : {}", trader.getTraderName());
		logger.info("Email : {}", trader.getEmailId());
		logger.info("Contact No  : {}", trader.getContactNo());
		logger.info("State : {}", trader.getTraderState());
		logger.info("City :  {}", trader.getCity());
		logger.info("Pin Code : {}", trader.getTraderPinCode());
		logger.info("WH ID : {}", trader.getWhId());
		return trader;
	}

	@Override
	public Commodity addCommodity(Commodity commodity) {
		logger.info("Commodity Name : {}", commodity.getCommodityName());
		logger.info("WH ID : {}", commodity.getWhAdminId());
		commodity.setCommodityId(commodityCounter++);
		commodities.put(commodityCounter, commodity);
		return commodity;
	}

	@Override
	public Category addCategory(Category category) {
		logger.info("Category Name : {}", category.getCategoryName());
		logger.info("Commodity Names : {}", category.getCommodityName());
		logger.info("WH Admin ID : {}", category.getWhAdminId());
		return category;
	}

	@Override
	public List<Commodity> retrieveWhAminId(String whAdminId) {
		List<Commodity> commoditiesList= new ArrayList<>();
		if(Integer.parseInt(whAdminId) == 1000) {
			for (Map.Entry<Integer, Commodity> commodity : commodities.entrySet())
			{
			    //System.out.println(entry.getKey() + "/" + entry.getValue());
				Commodity com= new Commodity();
				com.setCommodityId(commodity.getValue().getCommodityId());
				com.setCommodityName(commodity.getValue().getCommodityName());
				com.setWhAdminId(commodity.getValue().getWhAdminId());
				commoditiesList.add(com);
			}
		}
		return commoditiesList;
	}

}
