<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Profile</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/profile.css" />
<link href="https://fonts.googleapis.com/css2?family=Joan&display=swap"
	rel="stylesheet">
</head>
<body>
<header class="sticky-header">
        <div class="logo"><h1>The Yarnest Shop</h1></div>
        
        <nav>
            <ul>
                <li><a href="#" class="active">Home</a></li>
                <li><a href="products.jsp">Products</a></li>
                <li><a href="contact.jsp">Contact Us</a></li>
                <li><a href="about.jsp">About Us</a></li>
            </ul>
        </nav>
        <div class="search-bar">
            <input type="text" id="searchInput" placeholder="Search Crochet....">
            <button onclick="searchProducts()">🔍</button>
        </div>
    </header>
<main>
        <form class="profile-form" action="SubmitFormServlet" method="POST">
            <div class="form-group">
                <label for="fullName">Full Name</label>
                <input type="text" id="fullName" name="fullName" required>
            </div>
            <div class="form-group">
                <label for="phoneNumber">Phone Number</label>
                <input type="tel" id="phoneNumber" name="phoneNumber" pattern="[0-9]{10}" required>
                <small>Format: 1234567890</small>
            </div>
            <button type="submit" class="submit-btn">Submit</button>
        </form>
    </main>
</body>
</html>