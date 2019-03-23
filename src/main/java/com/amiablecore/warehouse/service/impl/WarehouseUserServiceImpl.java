package com.amiablecore.warehouse.service.impl;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Email;
import com.amiablecore.warehouse.beans.Inward;
import com.amiablecore.warehouse.beans.Outward;
import com.amiablecore.warehouse.beans.Trader;
import com.amiablecore.warehouse.config.EmailUtil;
import com.amiablecore.warehouse.dao.WarehouseUserDAO;
import com.amiablecore.warehouse.service.WarehouseUserService;

@Service("warehouseUserService")
public class WarehouseUserServiceImpl implements WarehouseUserService {

	private static Logger logger = LoggerFactory.getLogger(WarehouseUserServiceImpl.class);

	@Autowired
	private WarehouseUserDAO warehouseUserDAO;

	@Override
	public List<Category> retrieveCategories(Integer commodityId) {
		return warehouseUserDAO.retrieveCategories(commodityId);
	}

	@Override
	public List<Trader> retrieveTraders(String traderName) {
		return warehouseUserDAO.retrieveTraders(traderName);
	}

	@Override
	public List<Commodity> retrieveCommodities(Integer whAdminId) {
		return warehouseUserDAO.retrieveCommodities(whAdminId);
	}

	@Override
	public Inward retrieveLotDetails(Integer lotId) {
		return warehouseUserDAO.retrieveLotDetails(lotId);
	}

	@Override
	public List<Inward> retrieveLotList(String lotName) {
		return warehouseUserDAO.retrieveLotList(lotName);
	}

	@Override
	public Inward storeInwardDetails(Inward inward) {
		logger.info("Inward Lot Service :");
		return warehouseUserDAO.storeInwardDetails(inward);
	}

	@Override
	public Outward storeOutwardDetails(Outward outward) {
		return warehouseUserDAO.storeOutwardDetails(outward);
	}

	@Override
	public List<Inward> retrieveInCompleteInward(Integer whUserId) {
		return warehouseUserDAO.retrieveInCompleteInward(whUserId);
	}

	@Override
	public List<Outward> retrieveInCompleteOutward(Integer whUserId) {
		return warehouseUserDAO.retrieveInCompleteOutward(whUserId);
	}

	@Override
	public String updateTotalWeightInward(Integer inwardId, Double totalWeight) {
		return warehouseUserDAO.updateTotalWeightInward(inwardId, totalWeight);
	}

	@Override
	public String updateTotalWeightOutward(Outward outward) {
		return warehouseUserDAO.updateTotalWeightOutward(outward);
	}

	@Override
	public List<String> retrieveUnits() {
		return warehouseUserDAO.retrieveUnits();
	}

	@Override
	public boolean sendEmail(Email email) {
		final String fromEmail = "..."; // requires valid gmail id
		final String password = "..."; // correct password for gmail id

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		props.put("mail.smtp.port", "587"); // TLS Port
		props.put("mail.smtp.auth", "true"); // enable authentication
		props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);

		EmailUtil.sendEmail(session, email.getToEmail(), email.getSubject(), email.getMessage());
		return false;
	}

	@Override
	public List<String> retrieveGrades() {
		return warehouseUserDAO.retrieveGrades();
	}

}
