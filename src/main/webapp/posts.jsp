<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="header.jsp" />

<div class="container mt-5">
	<h1 class="mb-4">Posts</h1>

	<form method="POST" action="PostServlet">
		<div class="form-group">
			<label for="label">Input location:</label> 
			<input type="text"
				id="accountName" name="location" class="form-control"
				value="${filter.location}" />
		</div>

		<div class="form-group">
			<label for="content">Content:</label> <input type="text" id="content"
				name="content" class="form-control" value="${filter.content}" />
		</div>

		<div class="form-group">
			<label for="accountName">Account Name:</label> <input type="text"
				id="accountName" name="accountName" class="form-control"
				value="${filter.accountName}" />
		</div>

		<button type="submit" class="btn btn-primary">Filter</button>
	</form>

	<hr>

	<h2>Posts List</h2>
	<table class="table">
		<thead>
			<tr>
				<th>ID</th>
				<th>Content</th>
				<th>Status</th>
				<th>Created At</th>

				<c:if test="${sessionScope.user.role == 'MODERATE'}">
					<th>Action</th>
				</c:if>

			</tr>
		</thead>
		<tbody>
			<c:forEach var="post" items="${posts}" varStatus="counter">
				<tr>
					<td>${counter.count}</td>
					<td>${post.content}</td>
					<td><c:choose>
							<c:when test="${post.status == 'PENDING'}">
								<span class="badge badge-warning">Chờ Duyệt</span>
							</c:when>
							<c:when test="${post.status == 'APPROVED'}">
								<span class="badge badge-success">Chấp Thuận</span>
							</c:when>
							<c:when test="${post.status == 'REJECTED'}">
								<span class="badge badge-danger">Từ Chối</span>
							</c:when>
							<c:otherwise>
								<span class="badge badge-secondary">Unknown Status</span>
							</c:otherwise>
						</c:choose></td>
					<td>${post.createdAt}</td>

					<c:if test="${sessionScope.user.role == 'MODERATE'}">
						<td><a href="DetailPostServlet?id=${post.id}"
							class="btn btn-info">Detail</a></td>
					</c:if>

				</tr>
			</c:forEach>
		</tbody>
	</table>

	<nav aria-label="Page navigation">
		<ul class="pagination">
			<c:if test="${filter.currentPage > 1}">
				<li class="page-item"><a class="page-link"
					href="PostServlet?currentPage=${filter.currentPage - 1}"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
			</c:if>

			<c:forEach begin="1" end="${filter.totalPages}" var="page">
				<li class="page-item ${page == filter.currentPage ? 'active' : ''}">
					<a class="page-link" href="PostServlet?currentPage=${page}">${page}</a>
				</li>
			</c:forEach>

			<c:if test="${filter.currentPage < filter.totalPages}">
				<li class="page-item"><a class="page-link"
					href="PostServlet?currentPage=${filter.currentPage + 1}"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</c:if>
		</ul>
	</nav>
</div>

<jsp:include page="footer.jsp" />
