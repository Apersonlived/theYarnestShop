package com.theYarnestShop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.theYarnestShop.model.UserModel;
import com.theYarnestShop.services.UsersService;
import java.io.IOException;
import java.util.List;

@WebServlet("/Users")
public class UsersController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsersService userService;
    
    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UsersService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            List<UserModel> users = userService.getAllUsers();
            if (users == null) {
                throw new Exception("Unable to retrieve users from database");
            }
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/jspfiles/users.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error loading user list: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jspfiles/error.jsp").forward(request, response);
        }
    }
    
    @Override
    public void destroy() {
        if (userService != null) {
            userService.close();
        }
        super.destroy();
    }
}