package com.amiablecore.warehouse.beans;

/**
 * @author Pravin
 */
public class Outward implements Comparable<Outward> {
	private Integer outwardId;
	private Integer traderId;
	private Integer inwardId;
	private Integer whAdminId;
	private Integer whUserId;
	private String outwardDate;
	private Integer totalQuantity;
	private Double totalWeight;
	private Double bagWeight;
	private String lotName;

	public void setLotName(String lotName) {
		this.lotName = lotName;
	}

	public String getLotName() {

		return lotName;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public Integer getTraderId() {

		return traderId;
	}

	public void setInwardId(Integer inwardId) {
		this.inwardId = inwardId;
	}

	public Integer getInwardId() {
		return inwardId;
	}

	public void setBagWeight(Double bagWeight) {
		this.bagWeight = bagWeight;
	}

	public Double getBagWeight() {
		return bagWeight;
	}

	public String getOutwardDate() {
		return outwardDate;
	}

	public void setOutwardDate(String outwardDate) {
		this.outwardDate = outwardDate;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Integer getOutwardId() {
		return outwardId;
	}

	public void setOutwardId(Integer outwardId) {
		this.outwardId = outwardId;
	}

	public Integer getWhAdminId() {
		return whAdminId;
	}

	public void setWhAdminId(Integer whAdminId) {
		this.whAdminId = whAdminId;
	}

	public Integer getWhUserId() {
		return whUserId;
	}

	public void setWhUserId(Integer whUserId) {
		this.whUserId = whUserId;
	}

	@Override
	public int hashCode() {
		return inwardId * whAdminId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Inward)) {
			return false;
		}
		Inward out = (Inward) obj;
		return out.getInwardId().equals(this.inwardId);
	}

	@Override
	public int compareTo(Outward out) {
		return this.inwardId.compareTo(out.getInwardId());
	}
}
