<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý sản phẩm</title>
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
        }

        .btn-add {
            display: inline-block;
            background: #4caf50;
            padding: 10px 16px;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            margin: 20px 0;
            font-weight: bold;
        }

        .btn-add:hover {
            background: #45a049;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
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

        .action-link {
            color: #2980b9;
            text-decoration: none;
            font-weight: bold;
        }

        .action-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<h2>Danh sách sản phẩm</h2>
<a href="product_list?action=showAddForm" class="btn-add">➕ Thêm sản phẩm</a>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Giá</th>
        <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="product" items="${productList}">
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.price}</td>
            <td>
                <a class="action-link" href="product_list?action=edit&id=${product.id}">Sửa</a> |
                <a class="action-link" href="product_list?action=delete&id=${product.id}" onclick="return confirm('Xóa sản phẩm?');">Xóa</a>
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
