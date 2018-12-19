package com.amiablecore.warehouse.service;

import java.util.List;

import com.amiablecore.warehouse.beans.Category;

public interface WarehouseUserService {
	public List<Category> retrieveCategories(String whAdminId);
}
