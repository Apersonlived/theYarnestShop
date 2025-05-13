<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp" />
    <main>
        <form class="profile-form" action="${pageContext.request.contextPath}/Profile" method="POST">
            <div class="photo-side">
                <div class="user-img">
                    <c:if test="${not empty user.image}">
                        <img src="${user.image}" alt="Profile Picture">
                    </c:if>
                    <c:if test="${empty user.image}">
                        <i class="fas fa-user-circle default-avatar"></i>
                    </c:if>
                </div>
                <div class="form-group">
                    <label for="email">Email</label> 
                    <input type="email" id="email" name="email" value="${user.email}" required>
                </div>
                <div class="form-group">
                    <label for="phoneNumber">Phone Number</label> 
                    <input type="tel" id="phoneNumber" name="phoneNumber" value="${user.phone}" pattern="[0-9]{10}" required>
                </div>
                <div class="form-group">
                    <label for="address">Address</label> 
                    <input type="text" id="address" name="address" value="${user.address}" required>
                </div>
            </div>
            <div class="form-side">
                <h4>${user.full_name}</h4>
                <h5>ID: ${user.user_id}</h5>
                <div class="form-group">
                    <label for="username">User Name</label> 
                    <input type="text" id="username" name="username" value="${user.user_name}" required>
                </div>
                <div class="form-group">
                    <label for="rememberMe">
                        <input type="checkbox" id="rememberMe" name="rememberMe" 
                               ${rememberMe ? 'checked' : ''}> Remember me
                    </label>
                </div>
                <button type="submit" class="submit-btn">Update Profile</button>
            </div>
        </form>
    </main>
    
    <script>
        // Client-side validation
        document.querySelector('.profile-form').addEventListener('submit', function(e) {
            const phone = document.getElementById('phoneNumber').value;
            if (!/^\d{10}$/.test(phone)) {
                e.preventDefault();
                alert('Please enter a valid 10-digit phone number');
                return false;
            }
            return true;
        });
    </script>
</body>
</html>