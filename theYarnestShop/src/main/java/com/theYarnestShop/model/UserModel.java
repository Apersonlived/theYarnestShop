package com.theYarnestShop.model;

public class UserModel {
	private int user_id;
	private String user_name;
	private String email;
	private String phone;
	private String address;
	private String role;
	
	
	public UserModel(int user_id, String user_name, String email, String phone, String address, String role) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.role = role;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
