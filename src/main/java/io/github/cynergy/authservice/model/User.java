package io.github.cynergy.authservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	private String uid;
	private String regNo;
	private String password;
	private int clearance;

	/**
	 * Represents a user.
	 * This constructor is used to map the JSON body of the request to a POJO.
	 * 
	 * @param rollNumber
	 * @param password
	 */
	public User(@JsonProperty("reg_no") String rollNumber, @JsonProperty("password") String password) {
		this.uid = null;
		this.regNo = rollNumber;
		this.password = password;
		this.clearance = 50; // default
	}

	/**
	 * Represents a user.
	 * This constructor is used for database related stuff.
	 * @param uid
	 * @param rollNumber
	 * @param password
	 * @param clearance
	 */
	public User(String uid, String rollNumber, String password, int clearance) {
		this.uid = uid;
		this.regNo = rollNumber;
		this.password = password;
		this.clearance = clearance;
	}

	public String getRollNumber() {
		return regNo;
	}

	public void setRollNumber(String regNo) {
		this.regNo = regNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getClearance() {
		return clearance;
	}

	public void setClearance(int clearance) {
		this.clearance = clearance;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return String.format("User[id: %s, roll: %s, password: %s, clearance: %d]", this.uid, this.regNo,
				this.password, this.clearance);
	}
}
