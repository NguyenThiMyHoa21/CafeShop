<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý tài khoản</title>
    <style>
        body {
            margin: 0;
            font-family: "Segoe UI", sans-serif;
            background: #f5f6fa;
            padding: 30px;
        }

        h2 {
            text-align: center;
            color: #2f3640;
            margin-bottom: 10px;
        }

        .btn-add {
            display: inline-block;
            margin-bottom: 20px;
            padding: 10px 16px;
            background: #27ae60;
            color: white;
            border-radius: 6px;
            text-decoration: none;
            font-weight: bold;
        }

        .btn-add:hover {
            background: #1e8449;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            border-radius: 10px;
            overflow: hidden;
        }

        th, td {
            padding: 12px;
            text-align: center;
            border-bottom: 1px solid #eee;
        }

        th {
            background: #2f3640;
            color: white;
        }

        tr:nth-child(even) {
            background: #f9f9f9;
        }

        tr:hover {
            background: #e8f5e9;
        }

        .action-links a {
            margin: 0 5px;
            text-decoration: none;
            font-weight: bold;
            color: #e74c3c;
        }

        .action-links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<h2>Danh sách tài khoản</h2>
<a href="showAddUserForm" class="btn-add">Thêm tài khoản</a>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Tên đăng nhập</th>
        <th>Họ tên</th>
        <th>Quyền</th>
        <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.fullname}</td>
            <td>${user.role.name}</td>
            <td class="action-links">
                <a href="delete_user?id=${user.id}" onclick="return confirm('Xóa tài khoản này?');">Xóa</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="back-btn">
    <a href="home">← Quay về trang chủ</a>
</div>
</body>
</html>
