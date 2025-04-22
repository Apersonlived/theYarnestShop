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
</head>
<body>
	<div class="registration-image">
		<div class="registration-box">

			<h2>Register With Us</h2>

			<form action="#">

				<label for="fullname">Full Name </label> <input type="text"
					id="fullname" name="fullname" required> <label
					for="username">Username </label> <input type="text" id="username"
					name="username" required> <label for="email">Email
				</label> <input type="email" id="email" name="email" required> <label
					for="phone">Phone Number </label> <input type="tel" id="phone"
					name="phone" pattern="[0-9]{10}" required> <label
					for="address">Address</label> <input type="text" id="address"
					name="address" required> <label for="password">Password</label>
				<input type="password" id="password" name="password" required>



				<label for="confirmpassword">Confirm Password</label> <input
					type="password" id="confirmpassword" name="confirmpassword"
					required>

		<button type="submit" class="registration-button">Sign Up</button>

		</form>

	</div>
	</div>
</body>
</html>