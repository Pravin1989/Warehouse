package com.amiablecore.warehouse.beans;

/**
 * @author Pravin
 *
 */
public class Inward implements Comparable<Inward> {
	private Integer inwardId;
	private String inwardDate;
	private String lotName;
	private Integer traderId;
	private Integer commodityId;
	private Integer categoryId;
	private Integer totalQuantity;
	private Double weightPerBag;
	private Double totalWeight;
	private String physicalAddress;
	private Integer whAdminId;
	private Integer whUserId;
	private String unit;
	private String grade;
	private String vehicleNo;
	private String commodityName;
	private String categoryName;
	private boolean lotAlreadyPresent;
	private String whId;

	public void setWhAdminId(Integer whAdminId) {
		this.whAdminId = whAdminId;
	}

	public void setWhUserId(Integer whUserId) {
		this.whUserId = whUserId;
	}

	public Integer getWhAdminId() {
		return whAdminId;
	}

	public Integer getWhUserId() {
		return whUserId;
	}

	public String getLotName() {
		return lotName;
	}

	public void setLotName(String lotName) {
		this.lotName = lotName;
	}

	public void setInwardId(Integer inwardId) {
		this.inwardId = inwardId;
	}

	public void setInwardDate(String inwardDate) {
		this.inwardDate = inwardDate;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public void setWeightPerBag(Double weightPerBag) {
		this.weightPerBag = weightPerBag;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	public Integer getInwardId() {
		return inwardId;
	}

	public String getInwardDate() {
		return inwardDate;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public Double getWeightPerBag() {
		return weightPerBag;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public String getPhysicalAddress() {
		return physicalAddress;
	}

	// @Override
	// public int hashCode() {
	// return this.inwardId;
	// }
	//
	// @Override
	// public boolean equals(Object obj) {
	// if (obj == this) {
	// return true;
	// }
	// if (!(obj instanceof Outward)) {
	// return false;
	// }
	// Inward in = (Inward) obj;
	// return in.inwardId.equals(this.inwardId);
	// }

	@Override
	public int compareTo(Inward in) {
		return this.inwardId.compareTo(in.getInwardId());
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public boolean isLotAlreadyPresent() {
		return lotAlreadyPresent;
	}

	public void setLotAlreadyPresent(boolean lotAlreadyPresent) {
		this.lotAlreadyPresent = lotAlreadyPresent;
	}

	public String getWhId() {
		return whId;
	}

	public void setWhId(String whId) {
		this.whId = whId;
	}
}