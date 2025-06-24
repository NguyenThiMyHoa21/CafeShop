<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${product != null ? "Chỉnh sửa" : "Thêm"} sản phẩm</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            padding: 30px;
        }
        .container {
            max-width: 500px;
            margin: auto;
            background-color: #ffffff;
            padding: 25px 30px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #333;
        }
        label {
            font-weight: bold;
            display: block;
            margin-bottom: 6px;
            margin-top: 15px;
        }
        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            border-radius: 6px;
            border: 1px solid #ccc;
            margin-bottom: 10px;
            box-sizing: border-box;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 12px 18px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            width: 100%;
            font-size: 16px;
            margin-top: 10px;
        }
        button:hover {
            background-color: #45a049;
        }
        .error {
            color: red;
            text-align: center;
            margin-bottom: 15px;
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #555;
            text-decoration: none;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>${product != null ? "Chỉnh sửa" : "Thêm mới"} sản phẩm</h2>

    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/edit_product" method="post">
        <c:if test="${product != null}">
            <input type="hidden" name="id" value="${product.id}" />
        </c:if>

        <label for="name">Tên sản phẩm:</label>
        <input type="text" id="name" name="name" value="${product.name}" required />

        <label for="price">Giá:</label>
        <input type="number" step="0.01" id="price" name="price" value="${product.price}" required />

        <label for="category">Danh mục:</label>
        <input type="text" id="category" name="category" value="${product.category}" required />

        <label for="imagePath">Tên ảnh (ví dụ: tra_sua.jpg):</label>
        <input type="text" id="imagePath" name="imagePath" value="${product.imagePath}" />

        <button type="submit">Lưu sản phẩm</button>
    </form>

    <c:if test="${product != null}">
        <form action="${pageContext.request.contextPath}/edit_product" method="post" onsubmit="return confirm('Bạn có chắc muốn xóa sản phẩm này không?');">
            <input type="hidden" name="id" value="${product.id}" />
            <input type="hidden" name="action" value="delete" />
            <button type="submit" style="background-color:red; color:white;">Xóa sản phẩm</button>
        </form>
    </c:if>

    <a href="${pageContext.request.contextPath}/product_list" class="back-link">← Quay lại danh sách sản phẩm</a>
</div>
</body>
</html>
