package com.amiablecore.warehouse.dao;

import java.util.List;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Inward;
import com.amiablecore.warehouse.beans.Outward;
import com.amiablecore.warehouse.beans.Trader;

public interface WarehouseUserDAO {
	public List<Category> retrieveCategories(Integer commodityId);

	public List<Trader> retrieveTraders(String traderName);

	public List<Commodity> retrieveCommodities(Integer whAdminId);

	public Inward retrieveLotDetails(Integer lotName);

	public List<Inward> retrieveLotList(String lotName);

	public Inward storeInwardDetails(Inward inward);

	public Outward storeOutwardDetails(Outward outward);
}
