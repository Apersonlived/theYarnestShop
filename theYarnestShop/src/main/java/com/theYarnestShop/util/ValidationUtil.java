package com.theYarnestShop.util;

import java.util.regex.Pattern;

import jakarta.servlet.http.Part;

public class ValidationUtil {
	private static final Pattern ALPHABETIC_PATTERN = Pattern.compile("^[a-zA-Z]+(['-][a-zA-Z]+)*$");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
	private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{10}$");
	private static final Pattern ALPHA_NUMERIC_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]{3,20}$");
	private static final Pattern PASSWORD_PATTERN = Pattern
			.compile("^(?=.[A-Z])(?=.\\d)(?=.[@$!%?&])[A-Za-z\\d@$!%*?&]{8,}$");
	private static final Pattern ROLES_PATTERN = Pattern.compile("^(Customer|Admin)$");

	// Validate if a field is null or empty
	public boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	// Validate if a string contains only letters
	public boolean isAlphabetic(String value) {
		return value != null && ALPHABETIC_PATTERN.matcher(value).matches();
	}

	// Validate if a string starts with a letter and contains letters and numbers
	public boolean isAlphanumeric(String value) {
		return value != null && ALPHA_NUMERIC_PATTERN.matcher(value).matches();
	}

	// Validate if a string is a valid email address
	public boolean isValidEmail(String email) {
		return email != null && EMAIL_PATTERN.matcher(email).matches();
	}

	// Validate if a number is of 10 digits
	public boolean isValidPhoneNumber(String phone) {
		return phone != null && PHONE_PATTERN.matcher(phone).matches();
	}

	// Validate if a password is composed of at least a capital letter, a symbol or
	// a number
	public boolean isValidPassword(String password) {
		return password != null && PASSWORD_PATTERN.matcher(password).matches();
	}

	// Validate if a number is of 10 digits
	public boolean isValidRole(String role) {
		return role != null && ROLES_PATTERN.matcher(role).matches();
	}

	// Validate if password and retype password match
	public boolean passwordMatching(String password, String retypePassword) {
		return password != null && password.equals(retypePassword);
	}

	// Validate if a file extension and image extensions (jpg, jpeg, png, gif) match
	public boolean isValidImageExtension(Part imagePath) {
		if (imagePath == null || isNullOrEmpty(imagePath.getSubmittedFileName())) {
			return false;
		}
		String fileName = imagePath.getSubmittedFileName().toLowerCase();
		return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")
				|| fileName.endsWith(".gif");
	}
}
