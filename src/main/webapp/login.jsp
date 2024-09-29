<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Login</title>

<!-- Styling (optional) -->
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	text-align: center;
}

.login-container {
	margin-top: 50px;
}

.g_id_signin {
	margin: 20px;
}
</style>
</head>
<body>
	<div class="login-container">
		<h1>Login</h1>

		<!-- Show any error messages -->
		<c:if test="${not empty error}">
			<div class="error-message">${error}</div>
		</c:if>

		<a
			href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/complain-project/googleLogin&response_type=code&client_id=737347490351-dr5r55k9pjvtcrgk822il7aeodvhc58d.apps.googleusercontent.com&approval_prompt=force">Login
			With Google</a>


		<div class="g_id_signin" data-type="standard" data-theme="outline"
			data-size="large"></div>

		<!-- Optional: Manual Login Form -->
		<form action="LoginServlet" method="post">
			<h3>Or login with your account</h3>
			<div>
				<label for="username">Username:</label> <input type="text"
					id="username" name="username" required>
			</div>
			<div>
				<label for="password">Password:</label> <input type="password"
					id="password" name="password" required>
			</div>
			<div>
				<button type="submit">Login</button>
			</div>
		</form>

	</div>
</body>
</html>
