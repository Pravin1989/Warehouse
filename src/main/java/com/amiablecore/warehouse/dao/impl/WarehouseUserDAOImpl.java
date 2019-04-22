package com.amiablecore.warehouse.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
import com.amiablecore.warehouse.beans.Inward;
import com.amiablecore.warehouse.beans.Outward;
import com.amiablecore.warehouse.beans.Trader;
import com.amiablecore.warehouse.config.EmailUtil;
import com.amiablecore.warehouse.config.SMSUtil;
import com.amiablecore.warehouse.dao.WarehouseUserDAO;

@Repository("warehouseUserDAO")
public class WarehouseUserDAOImpl implements WarehouseUserDAO {

	private static Logger logger = LoggerFactory.getLogger(WarehouseUserDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private List<Inward> inwardList;

	private Inward updateToInwardComplete;

	private Inward updateToInwardPartiallyComplete;

	@Value(value = "${tablePrefix}")
	private String tablePrefix;

	SimpleDateFormat formatterUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	SimpleDateFormat formatterIndia = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public List<Category> retrieveCategories(Integer commodityId) {
		// Need to retrieve cateryId and its name
		List<Category> categorieslist = new ArrayList<Category>();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
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
	public List<Trader> retrieveTraders(String traderName) {
		List<Trader> traderList = new ArrayList<Trader>();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Trader where LOWER(trader_name) like '%" + traderName.toLowerCase()
				+ "%' or LOWER(trader_id) like '%" + traderName.toLowerCase() + "%'");
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString());
		for (Map<String, Object> row : rows) {
			Trader trader = new Trader();
			trader.setTraderId((Integer) row.get("id"));
			trader.setTraderName((String) row.get("trader_name"));
			trader.setTraderState((String) row.get("state"));
			trader.setCity((String) row.get("city"));
			trader.setTraderPinCode((String) row.get("pin_code"));
			traderList.add(trader);
		}
		logger.info("Trader List Retrieved");
		return traderList;
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
	public Inward retrieveLotDetails(Integer lotId) {
		Inward inward = new Inward();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Inward where inward_id=" + lotId);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString());

		if (rows.size() != 0) {
			inward.setInwardId((Integer) rows.get(0).get("inward_id"));
			inward.setTraderId((Integer) rows.get(0).get("trader_id"));
			inward.setTotalWeight(((BigDecimal) rows.get(0).get("total_weight")).doubleValue());
			inward.setLotName((String) rows.get(0).get("lot_name"));
			inward.setTotalQuantity((Integer) rows.get(0).get("Total_Quantity"));
			inward.setWeightPerBag(((BigDecimal) rows.get(0).get("Weight_Per_Bag")).doubleValue());
			inward.setUnit((String) rows.get(0).get("unit"));
			inward.setGrade((String) rows.get(0).get("grade"));
			inward.setVehicleNo((String) rows.get(0).get("vehicle_no"));
			inward.setPhysicalAddress((String) rows.get(0).get("physical_address"));
			inward.setCommodityId((Integer) rows.get(0).get("commodity_id"));
			inward.setCategoryId((Integer) rows.get(0).get("category_id"));
		}

		StringBuilder selectCommodityQuery = new StringBuilder();
		selectCommodityQuery.append("select * from ");
		selectCommodityQuery.append(tablePrefix);
		selectCommodityQuery.append("Commodity where id=" + inward.getCommodityId());
		List<Map<String, Object>> commodityRow = jdbcTemplate.queryForList(selectCommodityQuery.toString());
		if (rows.size() != 0) {
			inward.setCommodityName((String) commodityRow.get(0).get("name"));
		}

		StringBuilder selectCategoryQuery = new StringBuilder();
		selectCategoryQuery.append("select * from ");
		selectCategoryQuery.append(tablePrefix);
		selectCategoryQuery.append("Category where cat_id=" + inward.getCategoryId());
		List<Map<String, Object>> categoryRow = jdbcTemplate.queryForList(selectCategoryQuery.toString());
		if (rows.size() != 0) {
			inward.setCategoryName((String) categoryRow.get(0).get("category_name"));
		}
		logger.info("Lot Details Retrieved");
		return inward;
	}

	@Override
	public List<Inward> retrieveLotList(String lotName) {
		List<Inward> inwardList = new ArrayList<Inward>();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Inward where LOWER(lot_name) like '%" + lotName.toLowerCase()
				+ "%' and ISNULL(isoutwardfullycomplete,'false') <>  'true' and total_weight <> " + -1
				+ " and is_sync_with_outward=" + "'" + true + "'");
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString());
		for (Map<String, Object> row : rows) {
			Inward inward = new Inward();
			inward.setInwardId((Integer) row.get("inward_id"));
			inward.setLotName((String) row.get("lot_name"));
			inwardList.add(inward);
		}
		logger.info("Lot List Retrieved");
		return inwardList;
	}

	@Override
	@Transactional
	public Inward storeInwardDetails(Inward inward) {
		formatterUTC.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Inward where lot_name=? and Wh_Admin_Id=?");
		Object arguments[] = { inward.getLotName(), inward.getWhAdminId() };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);
		Inward lot = new Inward();
		if (rows.size() == 0) {
			logger.info("Inward Lot Adding :");
			StringBuilder insertQuery = new StringBuilder();
			insertQuery.append("INSERT INTO ");
			insertQuery.append(tablePrefix);
			insertQuery.append("Inward( Weight_Per_Bag, Total_Quantity, total_weight, Inward_Date, ");
			insertQuery.append("Physical_Address, Lot_Name, Commodity_Id, Category_Id, Trader_Id, Wh_Admin_Id, ");
			insertQuery.append("wh_User_Id, is_sync_with_outward, unit, last_updated_by, last_updated_on, grade, ");
			insertQuery.append("vehicle_no)");
			insertQuery.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			KeyHolder holder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(insertQuery.toString(),
							Statement.RETURN_GENERATED_KEYS);

					SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
					try {
						java.util.Date date = sdf1.parse(inward.getInwardDate());
						java.sql.Date sqlStartDate;
						sqlStartDate = new java.sql.Date(date.getTime());
						ps.setDouble(1, inward.getWeightPerBag());
						ps.setInt(2, inward.getTotalQuantity());
						if (inward.getTotalWeight() != null)
							ps.setDouble(3, inward.getTotalWeight());
						else
							ps.setDouble(3, -1);
						ps.setDate(4, sqlStartDate);
						ps.setString(5, inward.getPhysicalAddress());
						ps.setString(6, inward.getLotName());
						ps.setInt(7, inward.getCommodityId());
						ps.setInt(8, inward.getCategoryId());
						ps.setInt(9, inward.getTraderId());
						ps.setInt(10, inward.getWhAdminId());
						ps.setInt(11, inward.getWhUserId());
						ps.setBoolean(12, true);
						ps.setString(13, inward.getUnit());
						ps.setInt(14, inward.getWhUserId());
						ps.setDate(15,
								new Date(formatterIndia.parse(formatterUTC.format(new java.util.Date())).getTime()));
						// ps.setDate(15, new Date(new java.util.Date().getTime()));
						ps.setString(16, inward.getGrade());
						ps.setString(17, inward.getVehicleNo());
					} catch (Exception e) {
						logger.error("Failed To Inward :", e);
					}
					return ps;
				}
			}, holder);

			Integer newlyAddedInwardId = 0;
			if (holder.getKeys().size() == 1) {
				BigDecimal b = new BigDecimal(holder.getKeys().get("GENERATED_KEYS").toString());
				newlyAddedInwardId = b.intValue();
			}
			lot.setInwardId(newlyAddedInwardId);
			lot.setCommodityId(inward.getCommodityId());
			lot.setLotName(inward.getLotName());
			lot.setWhAdminId(inward.getWhAdminId());
			lot.setTraderId(inward.getTraderId());
			lot.setLotAlreadyPresent(false);
			logger.info("Inward Lot Added :");
			sendEmailToTraderForInward(lot);
			return lot;
		} else {
			for (Map<String, Object> row : rows) {
				lot.setWhAdminId((Integer) row.get("Wh_Admin_Id"));
				lot.setLotName((String) row.get("Lot_Name"));
			}
			lot.setLotAlreadyPresent(true);
			logger.info("Lot Already Present");
		}

		return lot;
	}

	@Override
	@Transactional
	public Outward storeOutwardDetails(Outward outward) {

		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO ");
		insertQuery.append(tablePrefix);
		insertQuery.append("Outward(Inward_Id, Weight_Per_Bag, Total_Quantity, total_weight, Outward_Date, ");
		insertQuery.append("lot_name, Trader_Id, Wh_Admin_Id, wh_User_Id, unit, grade, vehicle_no)");
		insertQuery.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(insertQuery.toString(),
						Statement.RETURN_GENERATED_KEYS);
				java.sql.Date sqlEndDate;
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
				try {
					java.util.Date date = sdf1.parse(outward.getOutwardDate());
					sqlEndDate = new java.sql.Date(date.getTime());
					ps.setInt(1, outward.getInwardId());
					ps.setDouble(2, outward.getBagWeight());
					ps.setInt(3, outward.getTotalQuantity());
					if (outward.getTotalWeight() != null)
						ps.setDouble(4, outward.getTotalWeight());
					else
						ps.setDouble(4, -1);
					ps.setDate(5, sqlEndDate);
					ps.setString(6, outward.getLotName());
					ps.setInt(7, outward.getTraderId());
					ps.setInt(8, outward.getWhAdminId());
					ps.setInt(9, outward.getWhUserId());
					ps.setString(10, outward.getUnit());
					ps.setString(11, outward.getGrade());
					ps.setString(12, outward.getVehicleNo());
				} catch (Exception e) {
					logger.error("Failed To Outward :", e);
				}
				return ps;
			}
		}, holder);

		Integer newlyAddedOutwardId = 0;
		if (holder.getKeys().size() == 1) {
			BigDecimal b = new BigDecimal(holder.getKeys().get("GENERATED_KEYS").toString());
			newlyAddedOutwardId = b.intValue();
		}
		Outward outLot = new Outward();
		outLot.setOutwardId(newlyAddedOutwardId);
		logger.info("Outward Lot Added :");

		outward.setOutwardId(newlyAddedOutwardId);

		if (outward.getTotalWeight() == null) {
			StringBuilder updateInwardQuery = new StringBuilder();
			int[] types = { Types.BOOLEAN, Types.INTEGER };
			updateInwardQuery.append("UPDATE ");
			updateInwardQuery.append(tablePrefix);
			updateInwardQuery.append("Inward set is_sync_with_outward=? where inward_Id=?");
			Object arg[] = new Object[] { false, outward.getInwardId() };
			jdbcTemplate.update(updateInwardQuery.toString(), arg, types);
			logger.info("Total Weight is Null so Inward Updated with ");
		} else {
			updateInwardDetails(outward);
		}
		return outLot;
	}

	public void updateInwardDetails(Outward outward) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Inward where inward_id=?");
		inwardList = new ArrayList<>();
		Object arguments[] = new Object[] { outward.getInwardId() };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);
		for (Map<String, Object> row : rows) {
			Inward inward = new Inward();
			inward.setInwardId((Integer) row.get("inward_id"));
			inward.setTotalQuantity((Integer) row.get("total_quantity"));
			inward.setTotalWeight(((BigDecimal) row.get("total_weight")).doubleValue());
			inward.setWhUserId((Integer) row.get("wh_user_id"));
			inwardList.add(inward);
		}
		logger.info(" updateInwardDetails InwardList Size : {}", inwardList.size());
		updateToInwardPartiallyComplete = null;
		updateToInwardComplete = null;
		inwardList.forEach(in -> {
			if (in.getTotalQuantity().equals(outward.getTotalQuantity())
					&& in.getTotalWeight().equals(outward.getTotalWeight())) {
				updateToInwardComplete = new Inward();
				updateToInwardComplete.setInwardId(in.getInwardId());
				updateToInwardComplete.setWhUserId(in.getWhUserId());
				updateToInwardComplete.setTotalQuantity(0);
				updateToInwardComplete.setTotalWeight(0.0);
				logger.info("updateToIwnardCompleteList");
			}
			if (outward.getTotalQuantity() < in.getTotalQuantity()) {
				updateToInwardPartiallyComplete = new Inward();
				updateToInwardPartiallyComplete.setInwardId(in.getInwardId());
				updateToInwardPartiallyComplete.setWhUserId(in.getWhUserId());
				updateToInwardPartiallyComplete.setTotalWeight(in.getTotalWeight() - outward.getTotalWeight());
				updateToInwardPartiallyComplete.setTotalQuantity(in.getTotalQuantity() - outward.getTotalQuantity());
				logger.info("updateToIwnardPartiallyComplete");
			}
		});
		if (updateToInwardComplete != null) {
			int[] types = { Types.BOOLEAN, Types.BOOLEAN, Types.INTEGER, Types.DATE, Types.DOUBLE, Types.INTEGER,
					Types.INTEGER };
			StringBuilder updateCompleteQuery = new StringBuilder();
			updateCompleteQuery.append("UPDATE ");
			updateCompleteQuery.append(tablePrefix);
			updateCompleteQuery.append(
					"Inward set isOutwardFullyComplete=?, is_sync_with_outward=?, last_updated_by=?, last_updated_on=?, total_weight=?, total_quantity=? where inward_Id=?");
			try {
				Object arg[] = new Object[] { true, true, updateToInwardComplete.getWhUserId(),
						new Date(formatterIndia.parse(formatterUTC.format(new java.util.Date())).getTime()),
						updateToInwardComplete.getTotalWeight(), updateToInwardComplete.getTotalQuantity(),
						updateToInwardComplete.getInwardId() };
				jdbcTemplate.update(updateCompleteQuery.toString(), arg, types);
			} catch (Exception e) {
				logger.info("Exception Raised during Parsing Date");
			}

			logger.info("updateToInwardComplete Updated");
		}
		if (updateToInwardPartiallyComplete != null) {
			StringBuilder updatePartialQuery = new StringBuilder();
			int[] types = { Types.INTEGER, Types.DOUBLE, Types.BOOLEAN, Types.INTEGER, Types.DATE, Types.INTEGER };
			updatePartialQuery.append("UPDATE ");
			updatePartialQuery.append(tablePrefix);
			updatePartialQuery.append(
					"Inward set total_quantity=?, total_weight=?, is_sync_with_outward=?, last_updated_by=?, last_updated_on=? where inward_Id=?");
			try {
				Object arg[] = new Object[] { updateToInwardPartiallyComplete.getTotalQuantity(),
						updateToInwardPartiallyComplete.getTotalWeight(), true,
						updateToInwardPartiallyComplete.getWhUserId(),
						new Date(formatterIndia.parse(formatterUTC.format(new java.util.Date())).getTime()),
						updateToInwardPartiallyComplete.getInwardId(), };
				jdbcTemplate.update(updatePartialQuery.toString(), arg, types);
			} catch (Exception e) {
				logger.info("Exception Raised during Parsing Date");
			}
			logger.info("updateToInwardPartiallyComplete Updated");
		}

		// update remaining quantity
		int[] types1 = { Types.INTEGER, Types.INTEGER };
		StringBuilder updateOutwardQuery = new StringBuilder();
		updateOutwardQuery.append("UPDATE ");
		updateOutwardQuery.append(tablePrefix);
		updateOutwardQuery.append(
				"Outward set remaining_qty=(select total_quantity from dbo.Inward where inward_Id=?) where outward_Id=?");
		Object arg1[] = new Object[] { outward.getInwardId(), outward.getOutwardId() };
		jdbcTemplate.update(updateOutwardQuery.toString(), arg1, types1);
		logger.info("updateToInwardComplete Updated");

		sendEmailToTraderForOutward(outward);
	}

	@Override
	public List<Inward> retrieveInCompleteInward(Integer whUserId) {
		List<Inward> inwardList = new ArrayList<Inward>();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Inward where wh_User_Id=? and total_weight= " + -1);
		Object arguments[] = { whUserId };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);
		for (Map<String, Object> row : rows) {
			Inward inward = new Inward();
			inward.setInwardId((Integer) row.get("inward_id"));
			inward.setLotName((String) row.get("lot_name"));
			// inward.setTotalWeight(((BigDecimal) row.get("total_weight")).doubleValue());
			inwardList.add(inward);
		}
		logger.info("InComplete Inward Lot List Retrieved");
		return inwardList;
	}

	@Override
	public List<Outward> retrieveInCompleteOutward(Integer whUserId) {
		List<Outward> outwardList = new ArrayList<Outward>();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Outward where wh_User_Id=? and total_weight= " + -1);
		Object arguments[] = { whUserId };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);
		for (Map<String, Object> row : rows) {
			Outward outward = new Outward();
			outward.setOutwardId((Integer) row.get("outward_id"));
			outward.setInwardId((Integer) row.get("inward_id"));
			outward.setLotName((String) row.get("lot_name"));
			outward.setTotalWeight(((BigDecimal) row.get("total_weight")).doubleValue());
			outward.setTotalQuantity((Integer) row.get("total_quantity"));
			outwardList.add(outward);
		}
		logger.info("InComplete Outward Lot List Retrieved");
		return outwardList;
	}

	@Override
	@Transactional
	public String updateTotalWeightInward(Integer inwardId, Double totalWeight) {
		StringBuilder updateTotalWeightInwardQuery = new StringBuilder();
		int[] types = { Types.DECIMAL, Types.INTEGER, };
		updateTotalWeightInwardQuery.append("UPDATE ");
		updateTotalWeightInwardQuery.append(tablePrefix);
		updateTotalWeightInwardQuery.append("Inward set total_weight=? where inward_Id=?");
		Object arg[] = new Object[] { totalWeight, inwardId };
		jdbcTemplate.update(updateTotalWeightInwardQuery.toString(), arg, types);
		logger.info("TotalWeight Inward Updated");
		return "";
	}

	@Override
	@Transactional
	public String updateTotalWeightOutward(Outward outward) {
		StringBuilder updateTotalWeightInwardQuery = new StringBuilder();
		int[] types = { Types.DECIMAL, Types.INTEGER, };
		updateTotalWeightInwardQuery.append("UPDATE ");
		updateTotalWeightInwardQuery.append(tablePrefix);
		updateTotalWeightInwardQuery.append("Outward set total_weight=? where outward_Id=?");
		Object arg[] = new Object[] { outward.getTotalWeight(), outward.getOutwardId() };
		jdbcTemplate.update(updateTotalWeightInwardQuery.toString(), arg, types);
		logger.info("TotalWeight Outward Updated");
		updateInwardDetails(outward);
		return "";
	}

	@Override
	public List<String> retrieveUnits() {
		List<String> unitList = new ArrayList<String>();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Units");
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString());

		for (Map<String, Object> row : rows) {
			unitList.add((String) row.get("unit_name"));
		}
		logger.info("Units Retrieved");
		return unitList;
	}

	@Override
	public List<String> retrieveGrades(Integer commodityId) {
		List<String> gradeList = new ArrayList<String>();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Grades where commodity_id=? and is_active=?");
		Object arguments[] = { commodityId, true };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);

		for (Map<String, Object> row : rows) {
			gradeList.add((String) row.get("grade_name"));
		}
		logger.info("Grades Retrieved");
		return gradeList;
	}

	public void sendEmailToTraderForInward(Inward inward) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select trader_email, trader_contact_no from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Trader where id=?");
		Object arguments[] = { inward.getTraderId() };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);
		String to = "";
		String number = "";
		if (rows.size() != 0) {
			to = rows.get(0).get("trader_email").toString();
			number = rows.get(0).get("trader_contact_no").toString();
		}

		StringBuilder selectInwardJoinQuery = new StringBuilder();
		selectInwardJoinQuery.append("select w.whid, c.name from ");
		selectInwardJoinQuery.append(tablePrefix);
		selectInwardJoinQuery.append("Warehouse w, ");
		selectInwardJoinQuery.append(tablePrefix);
		selectInwardJoinQuery.append("Inward i, ");
		selectInwardJoinQuery.append(tablePrefix);
		selectInwardJoinQuery.append("Commodity c ");
		selectInwardJoinQuery.append("where w.id=? and i.wh_admin_id=? and c.whid=? and c.id=?");
		Object arg[] = { inward.getWhAdminId(), inward.getWhAdminId(), inward.getWhAdminId(), inward.getCommodityId() };
		List<Map<String, Object>> row = jdbcTemplate.queryForList(selectInwardJoinQuery.toString(), arg);
		String whId = "";
		String commodity = "";
		if (row.size() != 0) {
			whId = row.get(0).get("whid").toString();
			commodity = row.get(0).get("name").toString();
		}

		Email email = new Email();
		StringBuilder message = new StringBuilder();
		message.append("Lot Inward");
		message.append("\n \n");
		message.append("ALERT:\n");
		message.append("Dear Customer, Your <b>" + commodity + "</b> commodity lot <b>(Lot No. " + inward.getLotName());
		message.append(")</b> has been deposited in " + "<b>" + whId);
		message.append("</b> warehouse.\nFor more report details, please log in to your <b>EZEE");
		message.append("WMS</b> account on www.ezeewms.com  or call on 70205 23599");
		email.setMessage(message.toString());
		email.setToEmail(to);
		email.setSubject("Lot Inward");
		EmailUtil.sendEmail(email);
		SMSUtil.sendSms(message.toString().replaceAll("<b>", "").replaceAll("</b>", ""), number);
		logger.info("Email Notification for Inward Done");
	}

	public void sendEmailToTraderForOutward(Outward out) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select trader_email, trader_contact_no from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Trader where id=?");
		Object arguments[] = { out.getTraderId() };
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);
		String to = "";
		String number = "";
		if (rows.size() != 0) {
			to = rows.get(0).get("trader_email").toString();
			number = rows.get(0).get("trader_contact_no").toString();
		}
		StringBuilder selectOutJoinQuery = new StringBuilder();
		selectOutJoinQuery.append("select w.whid, c.name from ");
		selectOutJoinQuery.append(tablePrefix);
		selectOutJoinQuery.append("Warehouse w, ");
		selectOutJoinQuery.append(tablePrefix);
		selectOutJoinQuery.append("Inward i, ");
		selectOutJoinQuery.append(tablePrefix);
		selectOutJoinQuery.append("Commodity c ");
		selectOutJoinQuery.append("where w.id=? and i.wh_admin_id=? and c.whid=? and c.id=");
		selectOutJoinQuery.append("(select commodity_id from dbo.Inward where inward_id=?)");
		Object arg[] = { out.getWhAdminId(), out.getWhAdminId(), out.getWhAdminId(), out.getInwardId() };
		List<Map<String, Object>> row = jdbcTemplate.queryForList(selectOutJoinQuery.toString(), arg);
		String whId = "";
		String commodity = "";
		if (row.size() != 0) {
			whId = row.get(0).get("whid").toString();
			commodity = row.get(0).get("name").toString();
		}

		Email email = new Email();
		StringBuilder message = new StringBuilder();
		message.append("Lot Outward");
		message.append("\n \n");
		message.append("ALERT:\n");
		message.append("Dear Customer, Your <b>" + commodity + "</b> commodity lot <b>(Lot No. " + out.getLotName());
		message.append(")</b> has been outward from " + "<b>" + whId);
		message.append("</b> warehouse.\nFor more report details, please log in to your <b>EZEE");
		message.append("WMS</b> account on www.ezeewms.com  or call on 9170205 23599");
		email.setMessage(message.toString());

		email.setToEmail(to);
		email.setSubject("Outward Done");
		EmailUtil.sendEmail(email);
		SMSUtil.sendSms(message.toString().replaceAll("<b>", "").replaceAll("</b>", ""), number);
		logger.info("Email Notification for Outward Done");
	}
}