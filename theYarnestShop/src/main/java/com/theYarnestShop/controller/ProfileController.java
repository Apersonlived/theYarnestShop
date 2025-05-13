package com.theYarnestShop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.theYarnestShop.model.UserModel;
import com.theYarnestShop.services.ProfileService;
import java.io.IOException;

@WebServlet("/Profile")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024,   // 1MB
	    maxFileSize = 5 * 1024 * 1024,     // 5MB
	    maxRequestSize = 10 * 1024 * 1024  // 10MB
	)

public class ProfileController extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProfileService profileService;
    
    @Override
    public void init() throws ServletException {
        profileService = new ProfileService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        try {
            if (session == null || session.getAttribute("userId") == null) {
                handleUnauthenticated(request, response);
                return;
            }

            int userId = (Integer) session.getAttribute("userId");
            String role = (String) session.getAttribute("role");
            
            // Role-based access control
            if (!"customer".equals(role)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            UserModel user = profileService.getUserProfile(userId);
            request.setAttribute("user", user);
            
            // Check for remember me cookie
            checkRememberMeCookie(request, response);
            
            request.getRequestDispatcher("/WEB-INF/jspfiles/profile.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error loading profile: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jspfiles/error.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        try {
            if (session == null || session.getAttribute("userId") == null) {
                handleUnauthenticated(request, response);
                return;
            }

            int userId = (Integer) session.getAttribute("userId");
            
            UserModel user = new UserModel();
            user.setUser_id(userId);
            user.setUser_name(request.getParameter("username"));
            user.setEmail(request.getParameter("email"));
            user.setPhone(request.getParameter("phoneNumber"));
            user.setAddress(request.getParameter("address"));
            
            boolean success = profileService.updateUserProfile(user);
            if (!success) {
                throw new Exception("Failed to update profile");
            }
            
            // Update session with new data
            session.setAttribute("username", user.getUser_name());
            
            // Handle remember me checkbox
            handleRememberMe(request, response, user.getUser_name());
            
            response.sendRedirect(request.getContextPath() + "/Profile");
        } catch (Exception e) {
            request.setAttribute("error", "Error updating profile: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jspfiles/error.jsp").forward(request, response);
        }
    }
    
    private void handleUnauthenticated(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/Login");
    }
    
    private void checkRememberMeCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rememberMe".equals(cookie.getName())) {
                    request.setAttribute("rememberMe", true);
                    break;
                }
            }
        }
    }
    
    private void handleRememberMe(HttpServletRequest request, HttpServletResponse response, String username) {
        String rememberMe = request.getParameter("rememberMe");
        Cookie rememberMeCookie = new Cookie("rememberMe", username);
        
        if ("on".equals(rememberMe)) {
            rememberMeCookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
            rememberMeCookie.setHttpOnly(true);
            rememberMeCookie.setPath(request.getContextPath());
        } else {
            rememberMeCookie.setMaxAge(0); // Delete cookie
        }
        
        response.addCookie(rememberMeCookie);
    }
    
    @Override
    public void destroy() {
        if (profileService != null) {
            profileService.close();
        }
    }
}