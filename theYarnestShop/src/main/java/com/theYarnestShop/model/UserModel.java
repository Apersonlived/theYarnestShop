package com.theYarnestShop.model;

public class UserModel {
	private Integer user_id;
	private String full_name;
	private String user_name;
	private String email;
	private String phone;
	private String address;
	private String password;
	private String role;
	
	public UserModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserModel(String user_name, String password) {
		super();
		this.user_name = user_name;
		this.password = password;
	}

	public UserModel(String full_name, String user_name, String email, String phone, String address, String password,
			String role) {
		super();
		this.full_name = full_name;
		this.user_name = user_name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.password = password;
		this.role = role;
	}

	public UserModel(Integer user_id, String full_name, String user_name, String email, 
			String phone, String address, String password, String role) {
		super();
		this.user_id = user_id;
		this.full_name = full_name;
		this.user_name = user_name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.password = password;
		this.role = role;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
