package com.amiablecore.warehouse.service;

import java.util.List;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Trader;

public interface WarehouseUserService {
	public List<Category> retrieveCategories(String commodityId);

	public List<Trader> retrieveTraders(String whAdminId);
	
	public List<Commodity> retrieveCommodities(String whAdminId);
}
