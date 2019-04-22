package com.amiablecore.warehouse.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
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
import com.amiablecore.warehouse.beans.Email;
import com.amiablecore.warehouse.beans.Grade;
import com.amiablecore.warehouse.beans.Trader;
import com.amiablecore.warehouse.beans.WarehouseUser;
import com.amiablecore.warehouse.config.EmailUtil;
import com.amiablecore.warehouse.config.SMSUtil;
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

	@Override
	@Transactional
	public WarehouseUser createUser(WarehouseUser user) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("WarehouseUser where contactNo=? and whAdminId=?");
		Object arguments[] = { user.getContactNo(), user.getWhAdminId() };
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
			if (holder.getKeys().size() == 1) {
				BigDecimal b = new BigDecimal(holder.getKeys().get("GENERATED_KEYS").toString());
				newUserId = b.intValue();
			}
			user.setUserId(newUserId);
			user.setAlreadyPresent(false);
			prepareMessageToUser(newUserId);
			logger.info("User Created");
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
		logger.info("User Already Present");
		return user;
	}

	@Override
	@Transactional
	public Trader createTrader(Trader trader) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Trader where trader_email=?");
		Object arguments[] = { trader.getEmailId() };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);
		if (rows.size() == 0) {
			StringBuilder insertQuery = new StringBuilder();
			insertQuery.append("INSERT INTO ");
			insertQuery.append(tablePrefix);
			insertQuery.append(
					"Trader(trader_name, trader_email, trader_contact_no, state, city, pin_code, is_active, is_sync, wh_id, trader_address)");
			insertQuery.append("VALUES(?,?,?,?,?,?,?,?,?,?)");
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
					ps.setString(10, trader.getTraderAddress());
					return ps;
				}
			}, holder);

			Integer newTraderId = 0;
			if (holder.getKeys().size() == 1) {
				BigDecimal b = new BigDecimal(holder.getKeys().get("GENERATED_KEYS").toString());
				newTraderId = b.intValue();
			}
			trader.setId(newTraderId);
			trader.setAlreadyPresent(false);
			prepareEmailAndMessageToTrader(newTraderId);
			logger.info("Trader Created");
			return trader;
		}
		for (Map<String, Object> row : rows) {
			trader.setId((Integer) row.get("id"));
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
		logger.info("Trader Already Present");
		return trader;
	}

	@Override
	@Transactional
	public Commodity addCommodity(Commodity commodity) {

		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Commodity where name=?  and whid=?");
		Object arguments[] = { commodity.getCommodityName(), commodity.getWhAdminId() };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);
		if (rows.size() == 0) {
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
					ps.setBoolean(2, true);
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
			logger.info("Commodity Created");
			return commodity;
		}
		for (Map<String, Object> row : rows) {
			commodity.setWhAdminId((Integer) row.get("whid"));
			commodity.setCommodityId((Integer) row.get("id"));
		}
		commodity.setAlreadyPresent(true);
		StringBuilder activateQuery = new StringBuilder();
		activateQuery.append("update ");
		activateQuery.append(tablePrefix);
		activateQuery.append("Commodity set isactive=? where id=? and whid=?");
		Object arg[] = { true, commodity.getCommodityId(), commodity.getWhAdminId() };
		int[] types = { Types.BOOLEAN, Types.BIGINT, Types.BIGINT };
		int activatedRows = jdbcTemplate.update(activateQuery.toString(), arg, types);
		logger.info("Commodity Activated : {}", activatedRows);
		logger.info("Commodity Already Present");
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
					ps.setBoolean(2, true);
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
			logger.info("Category Created");
			return category;
		}
		for (Map<String, Object> row : rows) {
			category.setWhAdminId((Integer) row.get("wh_id"));
			category.setCategoryId((Integer) row.get("cat_id"));
		}
		category.setAlreadyPresent(true);
		logger.info("Category Already Present");
		return category;
	}

	@Override
	public List<Commodity> retrieveCommodities(Integer whAdminId) {
		List<Commodity> commoditiesList = new ArrayList<>();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Commodity where whid=? and isactive=?");
		Object arguments[] = { whAdminId, true };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);

		for (Map<String, Object> row : rows) {
			Commodity commodity = new Commodity();
			commodity.setCommodityId((Integer) row.get("id"));
			commodity.setCommodityName((String) row.get("name"));
			commoditiesList.add(commodity);
		}
		logger.info("Commodities Retrieved");
		return commoditiesList;
	}

	@Override
	public List<Category> retrieveCategories(Integer commodityId) {
		List<Category> categorieslist = new ArrayList<Category>();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select top 10 * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Category where commodity_id=? and is_active=?");
		Object arguments[] = { commodityId, true };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);

		for (Map<String, Object> row : rows) {
			Category category = new Category();
			category.setCategoryId((Integer) row.get("cat_id"));
			category.setCategoryName((String) row.get("category_name"));
			categorieslist.add(category);
		}
		logger.info("Categories Retrieved");
		return categorieslist;
	}

	@Override
	@Transactional
	public Grade addGrade(Grade grade, String commodityId) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Grades where wh_id=? and commodity_id=? and grade_name=?");
		Object arguments[] = { grade.getWhAdminId(), Integer.parseInt(commodityId), grade.getGradeName() };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);
		if (rows.size() == 0) {
			StringBuilder insertQuery = new StringBuilder();
			insertQuery.append("INSERT INTO ");
			insertQuery.append(tablePrefix);
			insertQuery.append("Grades(grade_name, is_active, is_sync, wh_id, commodity_id)");
			insertQuery.append("VALUES(?,?,?,?,?)");
			KeyHolder holder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(insertQuery.toString(),
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, grade.getGradeName());
					ps.setBoolean(2, true);
					ps.setBoolean(3, grade.isSync());
					ps.setInt(4, grade.getWhAdminId());
					ps.setInt(5, Integer.parseInt(commodityId));
					return ps;
				}
			}, holder);

			Integer newGradeId = 0;
			if (holder.getKeys().size() > 1) {
				newGradeId = (Integer) holder.getKeys().get("grade_id");
			}
			grade.setGradeId(newGradeId);
			grade.setAlreadyPresent(false);
			logger.info("Grade Created");
			return grade;
		}
		for (Map<String, Object> row : rows) {
			grade.setWhAdminId((Integer) row.get("wh_id"));
			grade.setGradeId((Integer) row.get("grade_id"));
		}
		grade.setAlreadyPresent(true);
		logger.info("Grade Already Present");
		return grade;
	}

	@Override
	public List<Grade> retrieveGrades(Integer commodityId) {
		List<Grade> gradeList = new ArrayList<Grade>();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Grades where commodity_id=? and is_active=?");
		Object arguments[] = { commodityId, true };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);

		for (Map<String, Object> row : rows) {
			Grade grade = new Grade();
			grade.setGradeId((Integer) row.get("grade_id"));
			grade.setGradeName((String) row.get("grade_name"));
			gradeList.add(grade);
		}
		logger.info("Grades Retrieved");
		return gradeList;
	}

	@Override
	public Boolean removeCommodity(Commodity commodity) {
		StringBuilder deleteQuery = new StringBuilder();
		deleteQuery.append("update ");
		deleteQuery.append(tablePrefix);
		deleteQuery.append("Commodity set isactive=? where id=? and whid=?");
		Object arguments[] = { false, commodity.getCommodityId(), commodity.getWhAdminId() };
		int[] types = { Types.BOOLEAN, Types.BIGINT, Types.BIGINT };
		int rows = jdbcTemplate.update(deleteQuery.toString(), arguments, types);
		logger.info("Commodity DeActivated : {}", rows);
		return rows == 1;
	}

	@Override
	public Boolean removeCategory(Category category) {
		StringBuilder deleteQuery = new StringBuilder();
		deleteQuery.append("update ");
		deleteQuery.append(tablePrefix);
		deleteQuery.append("Category set is_active=? where cat_id=? and wh_id=?");
		Object arguments[] = { false, category.getCategoryId(), category.getWhAdminId() };
		int[] types = { Types.BOOLEAN, Types.BIGINT, Types.BIGINT };
		int rows = jdbcTemplate.update(deleteQuery.toString(), arguments, types);
		logger.info("Category DeActivated : {}", rows);
		return rows == 1;
	}

	@Override
	public Boolean removeGrade(Grade grade) {
		StringBuilder deleteQuery = new StringBuilder();
		deleteQuery.append("update ");
		deleteQuery.append(tablePrefix);
		deleteQuery.append("Grades set is_active=? where grade_id=? and wh_id=?");
		Object arguments[] = { false, grade.getGradeId(), grade.getWhAdminId() };
		int[] types = { Types.BOOLEAN, Types.BIGINT, Types.BIGINT };
		int rows = jdbcTemplate.update(deleteQuery.toString(), arguments, types);
		logger.info("Grade DeActivated : {}", rows);
		return rows == 1;
	}

	@Override
	public List<Category> retrieveCategoriesToRemove(Integer whAdminId) {
		List<Category> categorieslist = new ArrayList<Category>();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Category where wh_id=? and is_active=?");
		Object arguments[] = { whAdminId, true };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);

		for (Map<String, Object> row : rows) {
			Category category = new Category();
			category.setCategoryId((Integer) row.get("cat_id"));
			category.setCategoryName((String) row.get("category_name"));
			categorieslist.add(category);
		}
		logger.info("Categories Retrieved");
		return categorieslist;

	}

	@Override
	public List<Grade> retrieveGradesToRemove(Integer whAdminId) {
		List<Grade> gradeList = new ArrayList<Grade>();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Grades where wh_id=? and is_active=?");
		Object arguments[] = { whAdminId, true };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);

		for (Map<String, Object> row : rows) {
			Grade grade = new Grade();
			grade.setGradeId((Integer) row.get("grade_id"));
			grade.setGradeName((String) row.get("grade_name"));
			gradeList.add(grade);
		}
		logger.info("Grades Retrieved");
		return gradeList;
	}

	public void prepareEmailAndMessageToTrader(Integer id) {

		StringBuilder selectOutJoinQuery = new StringBuilder();
		selectOutJoinQuery.append("select T.trader_id, T.trader_email,T.trader_contact_no, T. trader_name, ");
		selectOutJoinQuery.append("L.Password from ");
		selectOutJoinQuery.append(tablePrefix);
		selectOutJoinQuery.append("Trader T,");
		selectOutJoinQuery.append(tablePrefix);
		selectOutJoinQuery.append("Login L where ");
		selectOutJoinQuery.append("L.UserId  = (select trader_id from Trader where id=?)");
		selectOutJoinQuery.append("and T.trader_id=(select trader_id from Trader where id=?)");
		Object arg[] = { id, id };
		List<Map<String, Object>> row = jdbcTemplate.queryForList(selectOutJoinQuery.toString(), arg);

		String traderId = "";
		String traderName = "";
		String traderEmail = "";
		String traderMobile = "";
		String traderPassword = "";
		if (row.size() != 0) {
			traderId = row.get(0).get("trader_id").toString();
			traderMobile = row.get(0).get("trader_contact_no").toString();
			traderEmail = row.get(0).get("trader_email").toString();
			traderName = row.get(0).get("trader_name").toString();
			traderPassword = row.get(0).get("password").toString();
		}
		Email email = new Email();
		StringBuilder message = new StringBuilder();
		message.append("Trader Registered");
		message.append("\n \n");
		message.append("ALERT:\n");
		message.append("Dear <b>" + traderName + "</b>,\n You are registered with <b> EZEEWMS </b> ");
		message.append("Your credentials are :" + "Id : <b>" + traderId);
		message.append("</b> and Password : <b>" + traderPassword + "</b>");
		message.append("\nPlease log in to your <b>EZEE");
		message.append("WMS</b> account on www.ezeewms.com  or call on 9170205 23599");
		email.setMessage(message.toString());

		email.setToEmail(traderEmail);
		email.setSubject("Trader Registered !!!");
		EmailUtil.sendEmail(email);
		SMSUtil.sendSms(message.toString().replaceAll("<b>", "").replaceAll("</b>", ""), traderMobile);
		logger.info("Email and SMS Notification for Trader Registration");
	}

	public void prepareMessageToUser(Integer id) {

		StringBuilder selectOutJoinQuery = new StringBuilder();
		selectOutJoinQuery.append("select * from ");
		selectOutJoinQuery.append(tablePrefix);
		selectOutJoinQuery.append("WarehouseUser where whuserid=?");
		Object arg[] = { id };
		List<Map<String, Object>> row = jdbcTemplate.queryForList(selectOutJoinQuery.toString(), arg);

		String whUserName = "";
		String whUserPassword = "";
		String whUserMobile = "";
		String whUserLoginId = "";
		if (row.size() != 0) {
			whUserName = row.get(0).get("whusername").toString();
			whUserPassword = row.get(0).get("password").toString();
			whUserMobile = row.get(0).get("contactno").toString();
			whUserLoginId = row.get(0).get("loginid").toString();
		}
		StringBuilder message = new StringBuilder();
		message.append("User Registered");
		message.append("\n \n");
		message.append("ALERT:\n");
		message.append("Dear " + whUserName + ",\n You are registered with EZEEWMS ");
		message.append("Your credentials are as follows. Id : " + whUserLoginId);
		message.append(" and Password : " + whUserPassword);
		message.append("\nPlease log in to your EZEE App.");

		SMSUtil.sendSms(message.toString(), whUserMobile);
		logger.info("SMS Notification for User Registration");
	}
}