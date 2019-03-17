package com.amiablecore.warehouse.beans;

/**
 * @author Pravin
 *
 */
public class Trader {
	private String traderName;
	private String emailId;
	private String city;
	private String contactNo;
	private String traderState;
	private String traderPinCode;
	private Integer whAdminId;
	private Integer traderId;
	private boolean active;
	private boolean sync;
	private boolean alreadyPresent;
	private String traderAddress;
	
	public boolean isActive() {
		return active;
	}

	public boolean isSync() {
		return sync;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}

	public void setAlreadyPresent(boolean alreadyPresent) {
		this.alreadyPresent = alreadyPresent;
	}

	public boolean isAlreadyPresent() {
		return alreadyPresent;
	}

	public Trader() {

	}

	public Trader(String traderName, Integer whAdminId, Integer traderId) {
		super();
		this.traderName = traderName;
		this.whAdminId = whAdminId;
		this.traderId = traderId;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getTraderState() {
		return traderState;
	}

	public void setTraderState(String traderState) {
		this.traderState = traderState;
	}

	public String getTraderPinCode() {
		return traderPinCode;
	}

	public void setTraderPinCode(String traderPinCode) {
		this.traderPinCode = traderPinCode;
	}

	public Integer getWhAdminId() {
		return whAdminId;
	}

	public void setWhAdminId(Integer whId) {
		this.whAdminId = whId;
	}

	public String getTraderAddress() {
		return traderAddress;
	}

	public void setTraderAddress(String traderAddress) {
		this.traderAddress = traderAddress;
	}
}
