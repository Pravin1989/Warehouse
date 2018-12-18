package com.amiablecore.warehouse.dao;

import java.util.List;

import com.amiablecore.warehouse.beans.Category;
import com.amiablecore.warehouse.beans.Commodity;
import com.amiablecore.warehouse.beans.Trader;
import com.amiablecore.warehouse.beans.WarehouseUser;

/**
 * @author Pravin
 *
 */
public interface WarehouseAdminDAO {

	public WarehouseUser createUser(WarehouseUser user);

	public Trader createTrader(Trader trader);

	public Commodity addCommodity(Commodity commodity);

	public Category addCategory(Category category);

	public List<Commodity> retrieveWhAminId(String whAdminId);
}