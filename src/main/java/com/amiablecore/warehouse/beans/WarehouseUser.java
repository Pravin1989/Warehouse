package com.amiablecore.warehouse.beans;

public class WarehouseUser {
    private String name;
    private String loginid;
    private String password;
    private String contactNo;
    private String whId;

    public void setWhId(String whId) {
        this.whId = whId;
    }

    public String getWhId() {
        return whId;
    }

    public String getName() {
        return name;
    }

    public String getLoginid() {
        return loginid;
    }

    public String getPassword() {
        return password;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

}
