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

	@Override
	public int hashCode() {
		return this.inwardId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Outward)) {
			return false;
		}
		Inward in = (Inward) obj;
		return in.inwardId.equals(this.inwardId);
	}

	@Override
	public int compareTo(Inward in) {
		return this.inwardId.compareTo(in.getInwardId());
	}
}