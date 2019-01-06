package com.amiablecore.warehouse.beans;

public class WarehouseUser {
	private Integer userId;
	private String name;
	private String loginId;
	private String password;
	private String contactNo;
	private String whAdminId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getWhAdminId() {
		return whAdminId;
	}

	public void setWhAdminId(String whAdminId) {
		this.whAdminId = whAdminId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
