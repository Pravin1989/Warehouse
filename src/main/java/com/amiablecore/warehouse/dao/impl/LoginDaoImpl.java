package com.amiablecore.warehouse.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Customer;
import com.amiablecore.warehouse.beans.LoginRequest;
import com.amiablecore.warehouse.beans.LoginResponse;
import com.amiablecore.warehouse.beans.UserType;
import com.amiablecore.warehouse.beans.WarehouseUser;
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
		// StringBuilder query = new StringBuilder();
		// query.append("select * from ");
		// query.append(tablePrefix);
		// query.append("table_customer where login_id=? ");
		// query.append("and customer_id=? and password=? ");
		// //Object arguments[] = { request.getLoginId(), request.getCustomerId(),
		// request.getLoginPassword() };
		// Object arguments[] = { request.getLoginId(), null, request.getLoginPassword()
		// };
		// List<Map<String, Object>> rows = jdbcTemplate.queryForList(query.toString(),
		// arguments);
		// Customer customer = new Customer();
		// for (Map<String, Object> row : rows) {
		// customer.setCustomerId((Long) row.get("customer_id"));
		// customer.setCustomerName((String) row.get("customer_name"));
		// customer.setAddress((String) row.get("customer_address"));
		// customer.setEmailId((String) row.get("email_id"));
		// customer.setGender((String) row.get("gender"));
		// }
		if (request.getUserType() == UserType.WH_ADMIN) {
			logger.info("Logged In as Admin");
			if (request.getLoginId().equals("admin") && request.getLoginPassword().equals("admin")) {
				response.setLoginIndicator(true);
				response.setLoggedInMessage("Login Is Done as Admin");
				response.setWhId("1000");
			} else {
				response.setLoginIndicator(false);
				response.setLoggedInMessage("Admin not present");
			}

		} else {
//			for (Map.Entry<Integer, WarehouseUser> users : WarehouseAdminDAOImpl.users.entrySet()) {
//				if (users.getValue().getLoginId().equals(request.getLoginId()) && users.getValue().getPassword().equals(request.getLoginPassword())) {
//					response.setWhId(users.getValue().getWhAdminId());
//					response.setUserId(users.getValue().getUserId());
//					response.setLoginIndicator(true);
//					response.setLoggedInMessage("Login Is Done as User");
//					logger.info("Logged In as User");
//					return response;
//				}
//			}
//			response.setLoginIndicator(false);
//			response.setLoggedInMessage("User not present");
//			logger.info("User Not Present");
			response.setWhId("1000");
			response.setUserId(100);
			response.setLoginIndicator(true);
		}
		// if (rows.size() != 0) {
		// response.setLoginIndicator(1);
		// response.setLoggedInMessage("Login Done !!");
		// response.setCustomer(customer);
		// }
			
			response.setLoggedInMessage("Login Is Done as User");
			logger.info("Logged In as User");
		return response;

	}
}
