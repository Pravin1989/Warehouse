package com.amiablecore.warehouse.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

	private List<Inward> updateToInwardCompleteList;

	private List<Inward> updateToInwardPartiallyCompleteList;

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
			inward.setTotalQuantity((Integer) rows.get(0).get("Total_Quntity"));
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
				+ "%' and isOutwardFullyComplete is not true");
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
	public void synchronizeInward(List<Inward> inwardList) {
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO ");
		insertQuery.append(tablePrefix);
		insertQuery.append("Inward( Weight_Per_Bag, Total_Quntity, total_weight, Inward_Date, Is_Sync, ");
		insertQuery.append("Physical_Address, Lot_Name, Comodity_Id, Category_Id, Trader_Id, Wh_Admin_Id, wh_User_Id)");
		insertQuery.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
		jdbcTemplate.batchUpdate(insertQuery.toString(), new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Inward inward = inwardList.get(i);
				java.sql.Date sqlStartDate;
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
				try {
					java.util.Date date = sdf1.parse(inward.getInwardDate());
					sqlStartDate = new java.sql.Date(date.getTime());
					ps.setDouble(1, inward.getWeightPerBag());
					ps.setInt(2, inward.getTotalQuantity());
					ps.setDouble(3, inward.getTotalWeight());
					ps.setDate(4, sqlStartDate);
					ps.setBoolean(5, true);
					ps.setString(6, inward.getPhysicalAddress());
					ps.setString(7, inward.getLotName());
					ps.setInt(8, inward.getCommodityId());
					ps.setInt(9, inward.getCategoryId());
					ps.setInt(10, inward.getTraderId());
					ps.setInt(11, inward.getWhAdminId());
					ps.setInt(12, inward.getWhUserId());
				} catch (Exception e) {
					logger.error("Failed To Inward :", e);
				}
			}

			@Override
			public int getBatchSize() {
				return inwardList.size();
			}
		});
	}

	@Override
	@Transactional
	public void synchronizeOutward(List<Outward> outwardList) {
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO ");
		insertQuery.append(tablePrefix);
		insertQuery.append("Outward(Inward_Id, Weight_Per_Bag, Total_Quantity, total_weight, Outward_Date, Is_Sync, ");
		insertQuery.append("Trader_Id, Wh_Admin_Id, wh_User_Id)");
		insertQuery.append("VALUES(?,?,?,?,?,?,?,?,?)");
		jdbcTemplate.batchUpdate(insertQuery.toString(), new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Outward outward = outwardList.get(i);
				java.sql.Date sqlStartDate;
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
				try {
					java.util.Date date = sdf1.parse(outward.getOutwardDate());
					sqlStartDate = new java.sql.Date(date.getTime());
					ps.setInt(1, outward.getInwardId());
					ps.setDouble(2, outward.getBagWeight());
					ps.setInt(3, outward.getTotalQuantity());
					ps.setDouble(4, outward.getTotalWeight());
					ps.setDate(5, sqlStartDate);
					ps.setBoolean(6, true);
					ps.setInt(7, outward.getTraderId());
					ps.setInt(8, outward.getWhAdminId());
					ps.setInt(9, outward.getWhUserId());
				} catch (Exception e) {
					logger.error("Failed To Outward :", e);
				}
			}

			@Override
			public int getBatchSize() {
				return outwardList.size();
			}
		});
		updateInwardDetails(outwardList);
	}

	public void updateInwardDetails(List<Outward> outwardList) {
		StringBuilder selectQuery = new StringBuilder();
		selectQuery.append("select Total_Quntity, total_weight, inward_id from ");
		selectQuery.append(tablePrefix);
		selectQuery.append("Inward where inward_id in(");
		inwardList = new ArrayList<>();
		updateToInwardCompleteList = new ArrayList<>();
		updateToInwardPartiallyCompleteList = new ArrayList<>();
		Object arguments[] = new Object[outwardList.size()];
		for (int index = 0; index < outwardList.size(); index++) {
			selectQuery.append("?");
			arguments[index] = outwardList.get(index).getInwardId();
			if (index != outwardList.size() - 1) {
				selectQuery.append(",");
			}
		}
		selectQuery.append(")");
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(selectQuery.toString(), arguments);
		for (Map<String, Object> row : rows) {
			Inward inward = new Inward();
			inward.setInwardId((Integer) row.get("inward_id"));
			inward.setTotalQuantity((Integer) row.get("total_quntity"));
			inward.setTotalWeight(((BigDecimal) row.get("total_weight")).doubleValue());
			inwardList.add(inward);
		}
		logger.info(" updateInwardDetails InwardList Size : {}", inwardList.size());
		logger.info(" updateInwardDetails OutwardList Size : {}", outwardList.size());
		for (int i = 0; i < outwardList.size(); i++) {
			Outward out = outwardList.get(i);
			if (inwardList.contains(out)) {
				Inward in = inwardList.get(i);
				if (in.getTotalQuantity().equals(out.getTotalQuantity())
						&& in.getTotalWeight().equals(out.getTotalWeight())) {
					updateToInwardCompleteList.add(in);
					logger.info("updateToIwnardCompleteList");
				}
				if (out.getTotalQuantity() < in.getTotalQuantity()) {
					Inward inUpdate = new Inward();
					inUpdate.setInwardId(in.getInwardId());
					inUpdate.setTotalWeight(in.getTotalWeight() - out.getTotalWeight());
					inUpdate.setTotalQuantity(in.getTotalQuantity() - out.getTotalQuantity());
					updateToInwardPartiallyCompleteList.add(inUpdate);
					logger.info("updateToIwnardPartiallyCompleteList");
				}

			}
		}
		if (updateToInwardCompleteList.size() != 0) {
			StringBuilder updateCompleteQuery = new StringBuilder();
			updateCompleteQuery.append("UPDATE ");
			updateCompleteQuery.append(tablePrefix);
			updateCompleteQuery.append("Inward set isOutwardFullyComplete=? where inward_Id=?");
			jdbcTemplate.batchUpdate(updateCompleteQuery.toString(), new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Inward inward = updateToInwardCompleteList.get(i);
					try {
						ps.setBoolean(1, true);
						ps.setInt(2, inward.getInwardId());
					} catch (Exception e) {
						logger.error("Failed To Update Complete Inward :", e);
					}
				}

				@Override
				public int getBatchSize() {
					return updateToInwardCompleteList.size();
				}
			});
			logger.info("updateToIwnardCompleteList Updated");
		}

		if (updateToInwardPartiallyCompleteList.size() != 0) {
			StringBuilder updatepartialQuery = new StringBuilder();
			updatepartialQuery.append("UPDATE ");
			updatepartialQuery.append(tablePrefix);
			updatepartialQuery.append("Inward set total_quntity=?, total_weight=? where inward_Id=?");
			jdbcTemplate.batchUpdate(updatepartialQuery.toString(), new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Inward inward = updateToInwardPartiallyCompleteList.get(i);
					try {
						ps.setInt(1, inward.getTotalQuantity());
						ps.setDouble(2, inward.getTotalWeight());
						ps.setInt(3, inward.getInwardId());
					} catch (Exception e) {
						logger.error("Failed To Update Partial Inward :", e);
					}
				}

				@Override
				public int getBatchSize() {
					return updateToInwardPartiallyCompleteList.size();
				}
			});
			logger.info("updateToIwnardPartiallyCompleteList Updated");
		}

	}
}