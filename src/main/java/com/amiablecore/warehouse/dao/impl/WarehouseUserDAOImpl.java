package com.amiablecore.warehouse.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Inward;
import com.amiablecore.warehouse.beans.Outward;
import com.amiablecore.warehouse.beans.Trader;
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

	@Override
	public List<Category> retrieveCategories(Integer commodityId) {
		// Need to retrieve cateryId and its name
		List<Category> categorieslist = new ArrayList<Category>();
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select * from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Category where commodity_id=?");
		Object arguments[] = { commodityId };
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
		selectQuery.append("Trader where LOWER(trader_name) like '%" + traderName.toLowerCase() + "%'");
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString());
		for (Map<String, Object> row : rows) {
			Trader trader = new Trader();
			trader.setTraderId((Integer) row.get("trader_id"));
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
		selectQuery.append("Commodity where whid=?");
		Object arguments[] = { whAdminId };
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
				+ "%' and isOutwardFullyComplete is not true and total_weight != " + -1);
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
		logger.info("Inward Lot Adding :");
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO ");
		insertQuery.append(tablePrefix);
		insertQuery.append("Inward( Weight_Per_Bag, Total_Quantity, total_weight, Inward_Date, ");
		insertQuery.append("Physical_Address, Lot_Name, Comodity_Id, Category_Id, Trader_Id, Wh_Admin_Id, wh_User_Id)");
		insertQuery.append("VALUES(?,?,?,?,?,?,?,?,?,?,?)");
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
				} catch (Exception e) {
					logger.error("Failed To Inward :", e);
				}
				return ps;
			}
		}, holder);

		Integer newlyAddedInwardId = 0;
		if (holder.getKeys().size() > 1) {
			newlyAddedInwardId = (Integer) holder.getKeys().get("inward_id");
		}
		Inward lot = new Inward();
		lot.setInwardId(newlyAddedInwardId);
		logger.info("Inward Lot Added :");
		return lot;
	}

	@Override
	@Transactional
	public Outward storeOutwardDetails(Outward outward) {
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO ");
		insertQuery.append(tablePrefix);
		insertQuery.append("Outward(Inward_Id, Weight_Per_Bag, Total_Quantity, total_weight, Outward_Date, ");
		insertQuery.append("lot_name, Trader_Id, Wh_Admin_Id, wh_User_Id)");
		insertQuery.append("VALUES(?,?,?,?,?,?,?,?,?)");
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
				} catch (Exception e) {
					logger.error("Failed To Outward :", e);
				}
				return ps;
			}
		}, holder);

		Integer newlyAddedOutwardId = 0;
		if (holder.getKeys().size() > 1) {
			newlyAddedOutwardId = (Integer) holder.getKeys().get("inward_id");
		}
		Outward outLot = new Outward();
		outLot.setOutwardId(newlyAddedOutwardId);
		logger.info("Outward Lot Added :");
		updateInwardDetails(outward);
		return outLot;
	}

	public void updateInwardDetails(Outward outward) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select Total_Quantity, total_weight, inward_id from ");
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
			inwardList.add(inward);
		}
		logger.info(" updateInwardDetails InwardList Size : {}", inwardList.size());
		inwardList.forEach(in -> {
			if (in.getTotalQuantity().equals(outward.getTotalQuantity())
					&& in.getTotalWeight().equals(outward.getTotalWeight())) {
				updateToInwardComplete = null;
				updateToInwardComplete = new Inward();
				updateToInwardComplete.setInwardId(in.getInwardId());
				logger.info("updateToIwnardCompleteList");
			}
			if (outward.getTotalQuantity() < in.getTotalQuantity()) {
				updateToInwardPartiallyComplete = null;
				updateToInwardPartiallyComplete = new Inward();
				updateToInwardPartiallyComplete.setInwardId(in.getInwardId());
				updateToInwardPartiallyComplete.setTotalWeight(in.getTotalWeight() - outward.getTotalWeight());
				updateToInwardPartiallyComplete.setTotalQuantity(in.getTotalQuantity() - outward.getTotalQuantity());
				logger.info("updateToIwnardPartiallyComplete");
			}
		});
		if (updateToInwardComplete != null) {
			int[] types = { Types.BOOLEAN, Types.INTEGER };
			StringBuilder updateCompleteQuery = new StringBuilder();
			updateCompleteQuery.append("UPDATE ");
			updateCompleteQuery.append(tablePrefix);
			updateCompleteQuery.append("Inward set isOutwardFullyComplete=? where inward_Id=?");
			Object arg[] = new Object[] { true, updateToInwardComplete.getInwardId() };
			jdbcTemplate.update(updateCompleteQuery.toString(), arg, types);
			logger.info("updateToInwardComplete Updated");
		}
		if (updateToInwardPartiallyComplete != null) {
			StringBuilder updatePartialQuery = new StringBuilder();
			int[] types = { Types.INTEGER, Types.DOUBLE, Types.INTEGER };
			updatePartialQuery.append("UPDATE ");
			updatePartialQuery.append(tablePrefix);
			updatePartialQuery.append("Inward set total_quantity=?, total_weight=? where inward_Id=?");
			Object arg[] = new Object[] { updateToInwardPartiallyComplete.getTotalQuantity(),
					updateToInwardPartiallyComplete.getTotalWeight(), updateToInwardPartiallyComplete.getInwardId() };
			jdbcTemplate.update(updatePartialQuery.toString(), arg, types);
			logger.info("updateToInwardPartiallyComplete Updated");
		}
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
			inward.setTotalWeight(((BigDecimal) row.get("total_weight")).doubleValue());
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
			outward.setLotName((String) row.get("lot_name"));
			outward.setTotalWeight(((BigDecimal) row.get("total_weight")).doubleValue());
			outwardList.add(outward);
		}
		logger.info("InComplete Outward Lot List Retrieved");
		return outwardList;
	}
}
