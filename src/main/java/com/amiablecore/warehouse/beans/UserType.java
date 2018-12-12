package com.amiablecore.warehouse.beans;

/**
 * @author Pravin
 *
 */
public enum UserType {
	WH_ADMIN, WH_USER;

	public static UserType fromString(String type) {
		return UserType.valueOf(type);
	}
}
