package com.theYarnestShop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.theYarnestShop.model.ProductModel;
import com.theYarnestShop.services.ProductService;
import java.io.IOException;
import java.util.List;

@WebServlet("/Products")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024,   // 1MB
	    maxFileSize = 5 * 1024 * 1024,     // 5MB
	    maxRequestSize = 10 * 1024 * 1024  // 10MB
	)
public class ProductListController extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductService productService;
    
    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if (action == null) {
                // List all products
                listProducts(request, response);
            } else if (action.equals("new")) {
                // Show add form
                request.getRequestDispatcher("/WEB-INF/jspfiles/addproduct.jsp").forward(request, response);
            } else if (action.equals("edit")) {
                // Show edit form
                String id = request.getParameter("id");
                ProductModel product = productService.getProductById(id);
                request.setAttribute("product", product);
                request.getRequestDispatcher("/WEB-INF/jspfiles/editproduct.jsp").forward(request, response);
            } else {
                listProducts(request, response);
            }
        } catch (Exception e) {
            // Handle error
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        try {
            if ("delete".equals(action)) {
                String id = request.getParameter("id");
                productService.deleteProduct(id);
            }
            response.sendRedirect("products");
        } catch (Exception e) {
            // Handle error
        }
    }
    
    private void listProducts(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        List<ProductModel> products = productService.getAllProducts();
        request.setAttribute("products", products);
        request.setAttribute("view", "list");
        request.getRequestDispatcher("/WEB-INF/jspfiles/productlist.jsp").forward(request, response);
    }
    
    private void showForm(HttpServletRequest request, HttpServletResponse response, String mode) 
            throws Exception {
        if ("edit".equals(mode)) {
            String id = request.getParameter("id");
            request.setAttribute("product", productService.getProductById(id));
        }
        request.setAttribute("view", mode);
        request.getRequestDispatcher("/WEB-INF/jspfiles/productlist.jsp").forward(request, response);
    }
    
    private void addProduct(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        ProductModel product = createProductFromRequest(request);
        productService.addProduct(product);
        response.sendRedirect(request.getContextPath() + "/Products");
    }
    
    private void updateProduct(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        ProductModel product = createProductFromRequest(request);
        product.setProduct_id(request.getParameter("product_id"));
        productService.updateProduct(product);
        response.sendRedirect(request.getContextPath() + "/Products");
    }
    
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) 
            throws Exception {
        String id = request.getParameter("id");
        productService.deleteProduct(id);
        response.sendRedirect(request.getContextPath() + "/Products");
    }
    
    private ProductModel createProductFromRequest(HttpServletRequest request) {
        ProductModel product = new ProductModel();
        product.setProduct_name(request.getParameter("product_name"));
        product.setCategory(request.getParameter("category"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(Float.parseFloat(request.getParameter("price")));
        product.setImage(request.getParameter("image"));
        product.setStock(Integer.parseInt(request.getParameter("stock")));
        return product;
    }
    
    @Override
    public void destroy() {
        if (productService != null) {
            productService.close();
        }
    }
}