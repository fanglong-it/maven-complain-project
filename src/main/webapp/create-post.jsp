<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<title>Tạo Bài Đăng</title>
</head>
<body>

	<jsp:include page="header.jsp" />

	<div class="container mt-5">
		<div id="app">
			<form action="CreatePostServlet" method="post">
				<div class="form-group">
					<label for="province">Chọn Thành Phố:</label> <select id="province"
						class="form-control" v-model="selectedProvince"
						@change="updateDistrictsAndWards" name="province">
						<option value="">Chọn Thành Phố</option>
						<option v-for="province in provinces" :key="province.code"
							:value="province.name">{{ province.name }}</option>
					</select>
				</div>

				<div class="form-group">
					<label for="district">Chọn Quận/Huyện:</label> <select
						id="district" class="form-control" v-model="selectedDistrict"
						@change="updateWards" name="district">
						<option value="">Chọn Quận/Huyện</option>
						<option v-for="district in districts" :key="district.code"
							:value="district.name">{{ district.name }}</option>
					</select>
				</div>

				<div class="form-group">
					<label for="ward">Chọn Phường/Xã:</label> <select id="ward"
						class="form-control" v-model="selectedWard" name="ward">
						<option value="">Chọn Phường/Xã</option>
						<option v-for="ward in wards" :key="ward.code" :value="ward.name">{{
							ward.name }}</option>
					</select>
				</div>

				<div class="form-group">
					<label for="location">Địa Chỉ:</label> <input type="text"
						id="location" class="form-control" name="location">
				</div>

				<div class="form-group">
					<label for="content">Nội Dung:</label>
					<textarea name="content" id="content" class="form-control" rows="5"
						required></textarea>
				</div>

				<button type="submit" class="btn btn-primary">Gửi Bài Đăng</button>
			</form>
		</div>
	</div>

	<jsp:include page="footer.jsp" />
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/vue@2.6.14/dist/vue.js"></script>
	<script>
	 window.base_url = "${pageContext.request.contextPath}";
	</script>
	<script src="apiData.js"></script>
	<!-- Include the external JS file -->
</body>
</html>
