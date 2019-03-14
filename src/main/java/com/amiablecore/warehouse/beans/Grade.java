package com.amiablecore.warehouse.beans;

/**
 * @author Pravin
 *
 */
public class Grade {
	private Integer gradeId;
	private String gradeName;
	private Integer whAdminId;
	private boolean active;
	private boolean sync;
	private boolean alreadyPresent;

	public void setAlreadyPresent(boolean alreadyPresent) {
		this.alreadyPresent = alreadyPresent;
	}

	public boolean isAlreadyPresent() {
		return alreadyPresent;
	}

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

	public Integer getWhAdminId() {
		return whAdminId;
	}

	public void setWhAdminId(Integer whAdminId) {
		this.whAdminId = whAdminId;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
}
