package com.amiablecore.warehouse.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

	public static Map<Integer, List<Category>> commodityCategories = new HashMap<>();

	private List<Category> categoriesList = new ArrayList<>();

	private Integer categoryCounter = 1;

	private Integer traderCounter = 10;

	private Integer commodityCounter = 100;

	private Integer userCounter = 1000;

	@Override
	public WarehouseUser createUser(WarehouseUser user) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("WarehouseUser where contactNo=?");
		Object arguments[] = { user.getContactNo() };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);
		if (rows.size() == 0) {
			StringBuilder insertQuery = new StringBuilder();
			insertQuery.append("INSERT INTO ");
			insertQuery.append(tablePrefix);
			insertQuery.append("WarehouseUser(WHUserName, LoginId, Password, ContactNo, WhAdminId)");
			insertQuery.append("VALUES(?,?,?,?,?)");
			KeyHolder holder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(insertQuery.toString(),
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, user.getName());
					ps.setString(2, user.getLoginId());
					ps.setString(3, user.getPassword());
					ps.setString(4, user.getContactNo());
					ps.setInt(5, user.getWhAdminId());
					return ps;
				}
			}, holder);

			Integer newUserId = 0;
			if (holder.getKeys().size() > 1) {
				newUserId = (Integer) holder.getKeys().get("whuserid");
			}
			user.setUserId(newUserId);
			user.setAlreadyPresent(false);
			return user;
		}
		for (Map<String, Object> row : rows) {
			user.setWhAdminId((Integer) row.get("whadminid"));
			user.setName((String) row.get("whusername"));
			user.setLoginId((String) row.get("loginid"));
			user.setPassword((String) row.get("password"));
			user.setContactNo((String) row.get("trader_contact_no"));
			user.setUserId((Integer) row.get("whuserid"));
		}
		user.setAlreadyPresent(true);
		return user;
	}

	@Override
	@Transactional
	public Trader createTrader(Trader trader) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Trader where trader_contact_no=? and trader_email=?");
		Object arguments[] = { trader.getContactNo(), trader.getEmailId() };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);
		if (rows.size() == 0) {
			StringBuilder insertQuery = new StringBuilder();
			insertQuery.append("INSERT INTO ");
			insertQuery.append(tablePrefix);
			insertQuery.append(
					"Trader(trader_name, trader_email, trader_contact_no, state, city, pin_code, is_active, is_sync, wh_id)");
			insertQuery.append("VALUES(?,?,?,?,?,?,?,?,?)");
			KeyHolder holder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(insertQuery.toString(),
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, trader.getTraderName());
					ps.setString(2, trader.getEmailId());
					ps.setString(3, trader.getContactNo());
					ps.setString(4, trader.getTraderState());
					ps.setString(5, trader.getCity());
					ps.setString(6, trader.getTraderPinCode());
					ps.setBoolean(7, trader.isActive());
					ps.setBoolean(8, trader.isSync());
					ps.setInt(9, trader.getWhAdminId());
					return ps;
				}
			}, holder);

			Integer newTraderId = 0;
			if (holder.getKeys().size() > 1) {
				newTraderId = (Integer) holder.getKeys().get("trader_id");
			}
			trader.setTraderId(newTraderId);
			trader.setAlreadyPresent(false);
			return trader;
		}
		for (Map<String, Object> row : rows) {
			trader.setTraderId((Integer) row.get("trader_id"));
			trader.setTraderName((String) row.get("trader_name"));
			trader.setEmailId((String) row.get("trader_email"));
			trader.setContactNo((String) row.get("trader_contact_no"));
			trader.setTraderState((String) row.get("state"));
			trader.setTraderState((String) row.get("city"));
			trader.setCity((String) row.get("pin_code"));
			trader.setActive((Boolean) row.get("is_active"));
			trader.setSync((Boolean) row.get("is_sync"));
			trader.setWhAdminId((Integer) row.get("wh_id"));
		}
		trader.setAlreadyPresent(true);
		return trader;
	}

	@Override
	@Transactional
	public Commodity addCommodity(Commodity commodity) {

		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Commodity where name=? ");
		Object arguments[] = { commodity.getCommodityName() };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);
		if (rows.size() == 0) {
			// Object[] params = new Object[] { commodity.getCommodityName(),
			// commodity.isActive(), commodity.isSync(),
			// commodity.getWhAdminId() };
			// int[] types = new int[] { Types.VARCHAR, Types.BOOLEAN, Types.BOOLEAN,
			// Types.INTEGER };
			StringBuilder insertQuery = new StringBuilder();
			insertQuery.append("INSERT INTO ");
			insertQuery.append(tablePrefix);
			insertQuery.append("Commodity(Name, IsActive, IsSync, WhId)");
			insertQuery.append("VALUES(?,?,?,?)");
			// jdbcTemplate.update(query.toString(), params, types);
			KeyHolder holder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(insertQuery.toString(),
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, commodity.getCommodityName());
					ps.setBoolean(2, commodity.isActive());
					ps.setBoolean(3, commodity.isSync());
					ps.setInt(4, commodity.getWhAdminId());
					return ps;
				}
			}, holder);

			Integer newCommodityId = 0;
			if (holder.getKeys().size() > 1) {
				newCommodityId = (Integer) holder.getKeys().get("id");
			}
			commodity.setCommodityId(newCommodityId);
			commodity.setAlreadyPresent(false);
			return commodity;
		}
		for (Map<String, Object> row : rows) {
			commodity.setWhAdminId((Integer) row.get("whid"));
			commodity.setCommodityId((Integer) row.get("id"));
		}
		commodity.setAlreadyPresent(true);
		return commodity;
	}

	@Override
	@Transactional
	public Category addCategory(Category category, String commodityId) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Category where wh_id=? and commodity_id=? and category_name=?");
		Object arguments[] = { category.getWhAdminId(), Integer.parseInt(commodityId), category.getCategoryName() };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);
		if (rows.size() == 0) {
			StringBuilder insertQuery = new StringBuilder();
			insertQuery.append("INSERT INTO ");
			insertQuery.append(tablePrefix);
			insertQuery.append("Category(category_name, is_active, is_sync, wh_id, commodity_id)");
			insertQuery.append("VALUES(?,?,?,?,?)");
			KeyHolder holder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(insertQuery.toString(),
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, category.getCategoryName());
					ps.setBoolean(2, category.isActive());
					ps.setBoolean(3, category.isSync());
					ps.setInt(4, category.getWhAdminId());
					ps.setInt(5, Integer.parseInt(commodityId));
					return ps;
				}
			}, holder);

			Integer newCategoryId = 0;
			if (holder.getKeys().size() > 1) {
				newCategoryId = (Integer) holder.getKeys().get("cat_id");
			}
			category.setCategoryId(newCategoryId);
			category.setAlreadyPresent(false);
			return category;
		}
		for (Map<String, Object> row : rows) {
			category.setWhAdminId((Integer) row.get("wh_id"));
			category.setCategoryId((Integer) row.get("cat_id"));
		}
		category.setAlreadyPresent(true);
		return category;
	}
}