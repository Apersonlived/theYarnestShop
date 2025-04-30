<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Register page</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/registration.css" />

<style>
.registration-image {
	width: 944px;
	height: 1414px;
	background-image:
		url("${pageContext.request.contextPath}/resources/image/registerpage.jpg");
}
</style>
<script>
    function validateForm(event) {
        const fullName = document.getElementById("fullname").value.trim();
        const userName = document.getElementById("username").value.trim();
        const email = document.getElementById("email").value.trim();
        const phone = document.getElementById("phone").value.trim();
        const address = document.getElementById("address").value.trim();
        const password = document.getElementById("password").value;
        const confirmPassword = document.getElementById("confirmpassword").value;

        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const phonePattern = /^\d{10}$/;
        const usernamePattern = /^[a-zA-Z0-9_]+$/;

        let errorMessage = "";

        if (!fullName) errorMessage += "Full name is required.\n";
        if (!userName || !usernamePattern.test(userName)) errorMessage += "Valid username (alphanumeric or _) is required.\n";
        if (!email || !emailPattern.test(email)) errorMessage += "Valid email is required.\n";
        if (!phone || !phonePattern.test(phone)) errorMessage += "Valid 10-digit phone number is required.\n";
        if (!address) errorMessage += "Address is required.\n";
        if (password.length < 6) errorMessage += "Password must be at least 6 characters.\n";
        if (password !== confirmPassword) errorMessage += "Passwords do not match.\n";

        if (errorMessage) {
            alert(errorMessage);
            event.preventDefault(); // Stop form submission
            return false;
        }

        return true;
    }
</script>

</head>
<body>
	<div class="registration-image">
		<div class="registration-box">

			<h2>Register With Us</h2>

			<form action="${pageContext.request.contextPath}/Register" method="POST" enctype="multipart/form-data" 
			onsubmit="return validateForm(event)">

				<label for="fullname">Full Name </label> <input type="text"
					id="fullname" name="fullname" required pattern="[a-zA-Z ]+" title="Name can only contain letters" required> 
					<label for="username">User Name</label>
                <input type="text" id="username" name="username" required pattern="[a-zA-Z0-9_]+" 
                title="Username can only contain letters, numbers, and underscores">

                <label for="email">Email</label>
                <input type="email" id="email" name="email" required pattern="[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}+" 
                title="Email must contain @---.com" required>

                <label for="phone">Phone Number</label>
                <input type="tel" id="phone" name="phone" required pattern="[0-9]{10}" title="Phone number must be 10 digits">

                <label for="address">Address</label>
                <input type="text" id="address" name="address" required pattern="[a-zA-Z0-9 ]+" 
                title="Address can only contain letters, spaces and numbers" required>

                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
				<label for="confirmpassword">Confirm Password</label> <input
					type="password" id="confirmpassword" name="confirmpassword"
					required>
					<h5 align = center><a href="${pageContext.request.contextPath}/Login">Already have an account? Log-in!</a></h5>

				<button type="submit" class="registration-button">Sign Up</button>

			</form>
			
		</div>
	</div>
</body>
</html>