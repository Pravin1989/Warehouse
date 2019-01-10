package com.amiablecore.warehouse.service;

import java.util.List;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Inward;
import com.amiablecore.warehouse.beans.Outward;
import com.amiablecore.warehouse.beans.Trader;

public interface WarehouseUserService {
	public List<Category> retrieveCategories(Integer commodityId);

	public List<Trader> retrieveTraders(String traderName);
	
	public List<Commodity> retrieveCommodities(Integer whAdminId);
	
	public Inward retrieveLotDetails(String lotId);
	
	public List<Inward> retrieveLotList(String lotName);
	
	public void synchronizeInward(List<Inward> inwardList, Integer whUserId);
	
	public String synchronizeOutward(List<Outward> outwardList);
}
