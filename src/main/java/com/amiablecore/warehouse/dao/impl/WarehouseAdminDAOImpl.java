package com.amiablecore.warehouse.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public static Map<Integer, Commodity> commodities = new HashMap<>();

	public static Map<Integer, WarehouseUser> users = new HashMap<>();

	public static Map<Integer, Category> categories = new HashMap<>();

	public static Map<Integer, Trader> traders = new HashMap<>();

	private Integer categoryCounter = 1;

	private Integer traderCounter = 10;

	private Integer commodityCounter = 100;

	private Integer userCounter = 1000;

	@Override
	public WarehouseUser createUser(WarehouseUser user) {
		logger.info("LoginId : {}", user.getLoginId());
		logger.info("Name : {}", user.getName());
		logger.info("Password  : {}", user.getPassword());
		logger.info("Contact No :  {}", user.getContactNo());
		logger.info("WH ID : {}", user.getWhAdminId());
		user.setUserId(userCounter++);
		users.put(userCounter, user);
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
		logger.info("WH ID : {}", trader.getWhAdminId());
		trader.setTraderId(traderCounter++);
		traders.put(traderCounter, trader);
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
		logger.info("WH Admin ID : {}", category.getWhAdminId());
		category.setCategoryId(categoryCounter++);
		categories.put(categoryCounter, category);
		return category;
	}

}