<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm người dùng</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 500px;
            margin: 60px auto;
            background: #fff;
            padding: 30px 40px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #333;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: bold;
            color: #333;
        }

        input[type="text"],
        input[type="password"],
        select {
            width: 100%;
            padding: 10px 12px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 15px;
        }

        .btn-group {
            text-align: center;
        }

        button {
            background-color: #28a745;
            border: none;
            padding: 10px 20px;
            color: white;
            font-size: 15px;
            border-radius: 6px;
            cursor: pointer;
            transition: background 0.3s;
        }

        button:hover {
            background-color: #218838;
        }

        .back-link {
            display: inline-block;
            margin-top: 15px;
            text-align: center;
            color: #007bff;
            text-decoration: none;
        }

        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Thêm người dùng mới</h2>

    <form action="add_user" method="post">
        <label for="username">Tên đăng nhập:</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Mật khẩu:</label>
        <input type="password" id="password" name="password" required>

        <label for="fullname">Họ tên:</label>
        <input type="text" id="fullname" name="fullname" required>

        <label for="roleId">Vai trò:</label>
        <select id="roleId" name="roleId" required>
            <option value="">-- Chọn vai trò --</option>
            <c:forEach var="role" items="${roles}">
                <option value="${role.id}">${role.name}</option>
            </c:forEach>
        </select>

        <div class="btn-group">
            <button type="submit">Lưu</button>
        </div>
    </form>

    <div class="btn-group">
        <a class="back-link" href="user_list">← Quay lại danh sách</a>
    </div>
</div>

</body>
</html>
