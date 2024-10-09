<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="fpt.uni.model.Post"%>
<%@ page import="fpt.uni.model.Account"%>
<html>
<head>
<title>Post Detail</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<!-- Include Header -->
	<div class="container">
		<h2>Post Detail</h2>
		<c:if test="${not empty post}">
			<p>
				<strong>Created By:</strong> ${post.username}
			</p>
			<p>
				<strong>Location:</strong> ${post.location}
			</p>
			<p>
				<strong>Content:</strong>
			</p>
			<p>${post.content}</p>
			<p>
				<strong>Status:</strong> ${post.status}
			</p>
			<p>
				<strong>Created At:</strong> ${post.createdAt}
			</p>
			<p>
				<strong>Updated At:</strong> ${post.updatedAt}
			</p>

			<!-- Approval Form -->
			<h3>Approve or Reject Post</h3>
			<form action="ApprovePostServlet" method="post">
				<input type="hidden" name="postId" value="${post.id}" />
				<div class="form-group">
					<label for="status">Choose Status:</label> <select name="status"
						class="form-control" required>
						<option value="APPROVED">Approve</option>
						<option value="REJECTED">Reject</option>
					</select>
				</div>
				<div class="form-group">
					<label for="comment">Comment:</label>
					<textarea name="comment" class="form-control" rows="3" required></textarea>
				</div>
				<button type="submit" class="btn btn-primary">Submit</button>
			</form>
		</c:if>
		<c:if test="${empty post}">
			<p>No post details available.</p>
		</c:if>
		<a href="PostServlet" class="btn btn-secondary">Back to Post List</a>
		<!-- Back to Post List -->
	</div>
	<jsp:include page="footer.jsp" />
	<!-- Include Footer -->
</body>
</html>