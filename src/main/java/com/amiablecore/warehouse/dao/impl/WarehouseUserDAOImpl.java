package com.amiablecore.warehouse.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.dao.WarehouseUserDAO;

@Repository("warehouseUserDAO")
public class WarehouseUserDAOImpl implements WarehouseUserDAO {

	private static Logger logger = LoggerFactory.getLogger(WarehouseUserDAOImpl.class);

	@Override
	public List<Category> retrieveCategories(String whAdminId) {
		List<Category> list = new ArrayList<Category>();
		for (Map.Entry<Integer, Category> category : WarehouseAdminDAOImpl.categories.entrySet()) {
			if (category.getValue().getWhAdminId().equals(whAdminId)) {
				list.add(category.getValue());
				logger.info("Category ");
			}
		}
		logger.info("Categories : {}", list);
		return list;
	}

}