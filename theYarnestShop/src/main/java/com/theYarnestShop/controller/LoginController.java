package com.theYarnestShop.controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.theYarnestShop.model.UserModel;
import com.theYarnestShop.services.LoginService;
import com.theYarnestShop.util.CookieUtil;
import com.theYarnestShop.util.RedirectionUtil;
import com.theYarnestShop.util.SessionUtil;
import com.theYarnestShop.util.ValidationUtil;

/**
 * 
 * @ author Ekata Baral
 */
@WebServlet(
		asyncSupported = true, 
		urlPatterns = { 
				"/Login"
		})

public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ValidationUtil validationUtil;
	private RedirectionUtil redirectionUtil;
	private LoginService loginService;

	@Override
	public void init() throws ServletException {
		this.validationUtil = new ValidationUtil();
		this.redirectionUtil = new RedirectionUtil();
		this.loginService = new LoginService();
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/jspfiles/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest req, HttpServletResponse res)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		if (!validationUtil.isNullOrEmpty(username) && !validationUtil.isNullOrEmpty(password)) {

			UserModel user = new UserModel(username, password);
			Boolean loginStatus = loginService.loginUser(user);

			if (loginStatus != null && loginStatus) {	
				SessionUtil.setAttribute(req, "username", username);
				if (username.equals("Admin")) {
					CookieUtil.addCookie(res, "role", "Admin", 5 * 30);
					res.sendRedirect(req.getContextPath() + "/Profile"); // Redirect to /home
				} else {
					CookieUtil.addCookie(res, "role", "Customer", 5 * 30);
					res.sendRedirect(req.getContextPath() + "/Profile"); // Redirect to /Profile
				}
			} else {
				handleLoginFailure(req, res, loginStatus);
				System.out.println("Failure to log-in, try entering valid credentials.");
			}
		} else {
			redirectionUtil.setMsgAndRedirect(req, res, "error", "Please fill all the fields!",
					RedirectionUtil.loginUrl);
		}
	}
	
	/**
	 * Handles login failures by setting attributes and forwarding to the login
	 * page.
	 *
	 * @param req         HttpServletRequest object
	 * @param res        HttpServletResponse object
	 * @param loginStatus Boolean indicating the login status
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	private void handleLoginFailure(HttpServletRequest req, HttpServletResponse res, Boolean loginStatus)
			throws ServletException, IOException {
		String errorMessage;
		if (loginStatus == null) {
			errorMessage = "Our server is under maintenance. Please try again later!";
		} else {
			errorMessage = "User credential do not match. Please try again!";
		}
		req.setAttribute("error", errorMessage);
		req.getRequestDispatcher(RedirectionUtil.loginUrl).forward(req, res);
	}

}
