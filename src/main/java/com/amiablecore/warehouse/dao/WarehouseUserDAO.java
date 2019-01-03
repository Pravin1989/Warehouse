package com.amiablecore.warehouse.dao;

import java.util.List;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Inward;
import com.amiablecore.warehouse.beans.Outward;
import com.amiablecore.warehouse.beans.Trader;

public interface WarehouseUserDAO {
	public List<Category> retrieveCategories(String commodityId);

	public List<Trader> retrieveTraders(String traderName);

	public List<Commodity> retrieveCommodities(String whAdminId);

	public Inward retrieveLotDetails(String lotName);

	public List<Inward> retrieveLotList(String lotName);

	public String synchronizeInward(List<Inward> inwardList);

	public String synchronizeOutward(List<Outward> outwardList);
}
