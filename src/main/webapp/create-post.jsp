<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>Tạo Bài Đăng</title>
</head>
<body>

    <jsp:include page="header.jsp" />

    <div class="container mt-5">
        <h2>Tạo Bài Đăng</h2>
        <form action="CreatePostServlet" method="post">
            <div class="form-group">
                <label for="labelId">Chọn Nhãn:</label>
                <select name="labelId" id="labelId" class="form-control" required>
                    <c:forEach var="label" items="${requestScope.labels}">
                        <option value="${label.id}">${label.city} - ${label.location}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="content">Nội Dung:</label>
                <textarea name="content" id="content" class="form-control" rows="5" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Gửi Bài Đăng</button>
        </form>
    </div>

    <jsp:include page="footer.jsp" />
    
</body>
</html>
