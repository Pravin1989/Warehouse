package com.amiablecore.warehouse.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.amiablecore.warehouse.beans.LoginRequest;
import com.amiablecore.warehouse.beans.LoginResponse;
import com.amiablecore.warehouse.beans.UserType;
import com.amiablecore.warehouse.dao.LoginDAO;

@Repository(value = "loginDao")
public class LoginDaoImpl implements LoginDAO {

	private static Logger logger = LoggerFactory.getLogger(LoginDaoImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Value(value = "${tablePrefix}")
	private String tablePrefix;

	@Override
	public LoginResponse processLogin(LoginRequest request) {
		LoginResponse response = new LoginResponse();
		StringBuilder query = new StringBuilder();
		query.append("select * from ");
		query.append(tablePrefix);
		if (request.getUserType() == UserType.WH_ADMIN) {
			query.append("Warehouse where whId=? ");
			query.append("and password=?");
			Object arguments[] = { request.getLoginId(), request.getLoginPassword() };
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString(), arguments);
			if (rows.size() == 0) {
				response.setLoginIndicator(false);
				response.setLoggedInMessage("Admin not present");
				return response;
			}
			Timestamp date, currentDate;
			for (Map<String, Object> row : rows) {
				logger.info("Logged In as Admin");
				response.setWhId((Integer) row.get("Id"));
				date = (Timestamp) row.get("expirydate");
				currentDate = new Timestamp(new java.util.Date().getTime());
				if (currentDate.getTime() > date.getTime()) {
					response.setAdminSubscriptionExpired(true);
				}
				response.setLoginIndicator(true);
				response.setLoggedInMessage("Login Is Done as Admin");
			}
		} else {
			query.append("WarehouseUser where loginid=? ");
			query.append("and password=? ");
			Object arguments[] = { request.getLoginId(), request.getLoginPassword() };
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString(), arguments);
			if (rows.size() == 0) {
				response.setLoginIndicator(false);
				response.setLoggedInMessage("WH User not present");
				return response;
			}
			for (Map<String, Object> row : rows) {
				logger.info("Logged In as User");
				response.setWhId((Integer) row.get("whadminid"));
				response.setUserId((Integer) row.get("whuserid"));
				response.setLoginIndicator(true);
				response.setLoggedInMessage("Login Is Done as User");
			}
			StringBuilder selectQuery = new StringBuilder();
			selectQuery.append("select * from ");
			selectQuery.append(tablePrefix);
			selectQuery.append("Warehouse where Id=? ");
			Object arg[] = { response.getWhId() };
			List<Map<String, Object>> warehouseRows = jdbcTemplate.queryForList(selectQuery.toString(), arg);
			Timestamp date, currentDate;
			date = (Timestamp) warehouseRows.get(0).get("expirydate");
			currentDate = new Timestamp(new java.util.Date().getTime());
			if (currentDate.getTime() > date.getTime()) {
				response.setAdminSubscriptionExpired(true);
			}
		}
		return response;
	}
}
