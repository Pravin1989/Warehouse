package com.amiablecore.warehouse.dao;

import java.util.List;

import com.amiablecore.warehouse.beans.Category;

public interface WarehouseUserDAO {
	public List<Category> retrieveCategories(String whAdminId);
}
