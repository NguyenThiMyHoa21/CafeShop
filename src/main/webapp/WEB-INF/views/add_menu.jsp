<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm Menu</title>
    <style>
        body {
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f6fa;
            margin: 0;
            padding: 40px;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            min-height: 100vh;
        }
        .container {
            background: white;
            padding: 30px 40px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            max-width: 400px;
            width: 100%;
        }
        h2 {
            margin-bottom: 24px;
            color: #2f3640;
            text-align: center;
        }
        form label {
            display: block;
            margin-bottom: 6px;
            font-weight: 600;
            color: #333;
        }
        form select, form input[type="text"] {
            width: 100%;
            padding: 10px 12px;
            margin-bottom: 20px;
            border: 1.5px solid #ccc;
            border-radius: 5px;
            font-size: 15px;
            transition: border-color 0.3s ease;
        }
        form input[type="text"]:focus,
        form select:focus {
            border-color: #2980b9;
            outline: none;
        }
        form input[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #2980b9;
            border: none;
            border-radius: 6px;
            color: white;
            font-size: 16px;
            font-weight: 700;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        form input[type="submit"]:hover {
            background-color: #1f6391;
        }
        .note {
            font-size: 13px;
            color: #555;
            margin-top: -15px;
            margin-bottom: 20px;
            font-style: italic;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Thêm mục menu mới</h2>
    <form action="addMenu" method="post">
        <label for="productId">Chọn sản phẩm:</label>
        <select name="productId" id="productId" required>
            <c:forEach var="product" items="${products}">
                <option value="${product.id}">${product.name}</option>
            </c:forEach>
        </select>

        <label for="url">URL liên kết:</label>
        <input type="text" id="url" name="url" required>

        <label for="roles">Vai trò (ngăn cách bằng dấu phẩy, ví dụ: ADMIN,STAFF):</label>
        <input type="text" id="roles" name="roles">
        <div class="note">Để trống nếu áp dụng cho tất cả vai trò.</div>

        <input type="submit" value="Thêm menu">
    </form>
</div>
</body>
</html>
