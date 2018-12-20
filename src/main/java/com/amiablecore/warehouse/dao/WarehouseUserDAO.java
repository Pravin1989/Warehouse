package com.amiablecore.warehouse.dao;

import java.util.List;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Trader;

public interface WarehouseUserDAO {
	public List<Category> retrieveCategories(String whAdminId);

	public List<Trader> retrieveTraders(String whAdminId);

	public List<Commodity> retrieveCommodities(String whAdminId);
}
