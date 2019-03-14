package com.amiablecore.warehouse.service;

import java.util.List;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Email;
import com.amiablecore.warehouse.beans.Inward;
import com.amiablecore.warehouse.beans.Outward;
import com.amiablecore.warehouse.beans.Trader;

public interface WarehouseUserService {
	public List<Category> retrieveCategories(Integer commodityId);

	public List<Trader> retrieveTraders(String traderName);

	public List<Commodity> retrieveCommodities(Integer whAdminId);

	public Inward retrieveLotDetails(Integer lotId);

	public List<Inward> retrieveLotList(String lotName);

	public Inward storeInwardDetails(Inward inward);

	public Outward storeOutwardDetails(Outward outward);

	public List<Inward> retrieveInCompleteInward(Integer whAdminId);

	public List<Outward> retrieveInCompleteOutward(Integer whAdminId);

	public String updateTotalWeightInward(Integer inwardId, Double totalWeight);

	public String updateTotalWeightOutward(Outward outward);

	public List<String> retrieveUnits();
	
	public boolean sendEmail(Email email);
	
	public List<String> retrieveGrades();
}