<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/users.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
</head>
<body>
    <%@ include file="header.jsp"%>

    <main class="user-list-container">
        <div class="action-header">
            <h2>Our Products</h2>
            <a href="${pageContext.request.contextPath}/products?action=new" class="btn btn-add">
                <i class="fas fa-plus"></i> Add New Product
            </a>
        </div>

        <div class="user-table-container">
            <table class="user-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Product Name</th>
                        <th>Category</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Image</th>
                        <th>Stock</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty products}">
                            <c:forEach var="product" items="${products}">
                                <tr>
                                    <td>${product.product_id}</td>
                                    <td>${product.product_name}</td>
                                    <td>${product.category}</td>
                                    <td>${product.description}</td>
                                    <td>$${product.price}</td>
                                    <td>
                                        <c:if test="${not empty product.image}">
                                            <img src="${product.image}" alt="${product.product_name}" class="product-image">
                                        </c:if>
                                    </td>
                                    <td>${product.stock}</td>
                                    <td>
                                        <div class="action-buttons">
                                            <a href="${pageContext.request.contextPath}/products?action=edit&id=${product.product_id}" 
                                               class="btn btn-edit">
                                                <i class="fas fa-edit"></i> Edit
                                            </a>
                                            <form action="${pageContext.request.contextPath}/products" method="post" 
                                                  style="display:inline;">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="id" value="${product.product_id}">
                                                <button type="submit" class="btn btn-delete" 
                                                        onclick="return confirm('Are you sure you want to delete this product?')">
                                                    <i class="fas fa-trash-alt"></i> Delete
                                                </button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="8">No products found</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>