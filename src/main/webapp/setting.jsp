<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Manage Settings</title>
<link rel="stylesheet" href="path/to/bootstrap.css">
<!-- Link to your Bootstrap CSS -->
</head>
<body>
	<jsp:include page="header.jsp" />
	<!-- Include Header -->
	<div class="container">
		<h2>Manage Settings</h2>
		<form action="${pageContext.request.contextPath}/SettingServlet"
			method="post">
			<div class="form-group">
				<label for="site_name">Site Name:</label> <input type="text"
					class="form-control" id="site_name" name="site_name"
					value="${settings['site_name']}" required>
			</div>
			<div class="form-group">
				<label for="support_email">Support Email:</label> <input
					type="email" class="form-control" id="support_email"
					name="support_email" value="${settings['support_email']}" required>
			</div>
			<div class="form-group">
				<label for="max_upload_size">Max Upload Size:</label> <input
					type="text" class="form-control" id="max_upload_size"
					name="max_upload_size" value="${settings['max_upload_size']}"
					required>
			</div>
			<div class="form-group">
				<label for="maintenance_mode">Maintenance Mode:</label> <select
					class="form-control" id="maintenance_mode" name="maintenance_mode">
					<option value="true"
						<c:if test="${settings['maintenance_mode'] == 'true'}">selected</c:if>>Enabled</option>
					<option value="false"
						<c:if test="${settings['maintenance_mode'] == 'false'}">selected</c:if>>Disabled</option>
				</select>
			</div>
			<div class="form-group">
				<label for="default_language">Default Language:</label> <input
					type="text" class="form-control" id="default_language"
					name="default_language" value="${settings['default_language']}"
					required>
			</div>
			<button type="submit" class="btn btn-primary">Update
				Settings</button>
		</form>
		<jsp:include page="footer.jsp" />
		<!-- Include Footer -->
	</div>
</body>
</html>
