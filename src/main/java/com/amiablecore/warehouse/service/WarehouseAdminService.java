package com.amiablecore.warehouse.service;

import java.util.List;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Trader;
import com.amiablecore.warehouse.beans.WarehouseUser;

/**
 * @author Pravin
 *
 */
public interface WarehouseAdminService {

	public WarehouseUser createUser(WarehouseUser customer);

	public Trader createTrader(Trader trader);

	public Commodity addCommodity(Commodity commodity);

	public Category addCategory(Category category);

	public List<Commodity> retrieveCommodities(String whAdminId);
}
