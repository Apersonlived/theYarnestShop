package com.theYarnestShop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.theYarnestShop.model.UserModel;
import com.theYarnestShop.services.RegisterService;
import com.theYarnestShop.util.PasswordUtil;
import com.theYarnestShop.util.ValidationUtil;

/**
 * 
 * @ author Ekata Baral
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/Register" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RegisterService registerService = new RegisterService();
	private ValidationUtil validationUtil = new ValidationUtil();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/jspfiles/registration.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest req, HttpServletResponse res)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("Inside doPost()");

		try {
			System.out.println("Registration is being processed");
			System.out.println("Extracting user data...");
			UserModel user = extractUserModel(req);
			System.out.println("User extracted: " + user.getUser_name());

			Boolean isAdded = registerService.addUser(user);
			 if (isAdded) {
	                System.out.println("Registration successful for user: " + user.getUser_name());
	                // Set success message and redirect to login
	                HttpSession session = req.getSession();
	                session.setAttribute("successMessage", "Registration successful! Please login.");
	                res.sendRedirect("Login");
	            } else {
	                System.out.println("Registration failed for user: " + user.getUser_name());
	                // To check for failures
	                String errorMsg = "Registration failed. ";
	                if (registerService.isUsernameExists(user.getUser_name())) {
	                    errorMsg += "Username already exists. ";
	                } else if(!validationUtil.isAlphanumeric(user.getUser_name())) {
	                	errorMsg += "Invalid username, try again! ";
	                }
	                if (registerService.isEmailExists(user.getEmail())) {
	                    errorMsg += "Email already exists. ";
	                } else if (!validationUtil.isValidEmail(user.getEmail())){
	                	errorMsg += "Invalid email id, try again!";
	                }
	                if (registerService.isPhoneExists(user.getPhone())) {
	                    errorMsg += "Phone already exists. ";
	                } else if (!validationUtil.isValidEmail(user.getPhone())){
	                	errorMsg += "Invalid phone number, try again!";
	                }
	                
	                if (errorMsg.equals("Registration failed.")) {
	                    errorMsg += "Please check the logs for more details.";
	                } 
	                
	                req.setAttribute("error", errorMsg);
	                req.getRequestDispatcher("/WEB-INF/jspfiles/registration.jsp").forward(req, res);
	            }
	        } catch (Exception e) {
	            System.err.println("Exception in registration process: " + e.getMessage());
	            e.printStackTrace();
	            req.setAttribute("error", "An error occurred: " + e.getMessage());
	            req.getRequestDispatcher("WEB-INF/jspfiles/registration.jsp").forward(req, res);
	        }
		}

	private UserModel extractUserModel(HttpServletRequest req) throws Exception {
		String fName = req.getParameter("fullname");
		String uName = req.getParameter("username");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");
		String password = req.getParameter("password");
		String cPassword = req.getParameter("confirmpassword");

		if (password == null || !password.equals(cPassword)) {
			throw new Exception("Password invalid or unmatched.");
		}

		password = PasswordUtil.encrypt(uName, password);

		return new UserModel(fName, uName, email, phone, address, password, "Customer");
	}
}
