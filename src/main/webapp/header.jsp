<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Complain Portal</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="path/to/your/custom.css">
<!-- Your custom CSS if needed -->
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="#">Complain Portal</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link" href="PostServlet">Posts</a>
				</li>
				<c:if test="${sessionScope.user.role == 'USER'}">
					<li class="nav-item"><a class="nav-link" href="CreatePostServlet">Create Post</a>
					</li>
				</c:if>
				<c:if test="${sessionScope.user.role == 'ADMIN'}">
					<li class="nav-item"><a class="nav-link" href="SettingServlet">Setting</a>
					</li>
				</c:if>

				<li class="nav-item"><a class="nav-link" href="LogoutServlet">Logout</a>
				</li>
			</ul>
		</div>
		<span class="navbar-text"> Welcome,
			${sessionScope.user.username} </span>
	</nav>