package com.theYarnestShop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.theYarnestShop.model.ProductModel;
import com.theYarnestShop.services.AdminService;

/**
 * Servlet implementation for handling admin dashboard requests
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/Admin" })
public class AdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminService adminService;

    /**
     * Default constructor initializes the AdminService
     */
    public AdminController() {
        this.adminService = new AdminService();
    }

    /**
     * Handles HTTP GET requests to display the admin dashboard
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Fetch all dashboard metrics
            double monthlySales = adminService.getMonthlySales();
            List<ProductModel> lowStockProducts = adminService.getLowStockProducts();
            Map<String, Integer> bestsellers = adminService.getBestsellers();
            int totalUsers = adminService.getTotalUsersCount();
            Map<String, Double> yearlyRevenue = adminService.getYearlyRevenue();

            // Set attributes for JSP
            request.setAttribute("monthlySales", String.format("%.2f", monthlySales));
            request.setAttribute("lowStockProducts", lowStockProducts);
            request.setAttribute("bestsellers", bestsellers);
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("yearlyRevenue", yearlyRevenue);

            // Forward to dashboard JSP
            request.getRequestDispatcher("/WEB-INF/jspfiles/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jspfiles/error.jsp").forward(request, response);
        }
    }

    /**
     * Handles HTTP POST requests for admin actions
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "updateStock":
                    handleUpdateStock(request, response);
                    break;
                case "refreshData":
                    handleRefreshData(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error processing action: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    /**
     * Handles stock update requests
     */
    private void handleUpdateStock(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("productId");
        int newStock = Integer.parseInt(request.getParameter("newStock"));
        
        try {
            boolean success = adminService.updateProductStock(productId, newStock);
            if (success) {
                request.setAttribute("success", "Stock updated successfully");
            } else {
                request.setAttribute("error", "Failed to update stock");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error updating stock: " + e.getMessage());
        }
        
        doGet(request, response);
    }

    /**
     * Handles data refresh requests
     */
    private void handleRefreshData(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Simply reload the dashboard with fresh data
        doGet(request, response);
    }
}