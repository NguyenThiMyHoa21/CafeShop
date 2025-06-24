<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách đơn gọi món</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            font-family: 'Roboto', sans-serif;
            background: #f5f6fa;
            padding: 30px;
            min-height: 100vh;
        }

        h2 {
            color: #2f3640;
            text-align: center;
        }

        .btn-add {
            display: inline-block;
            padding: 10px 16px;
            background: #4caf50;
            color: white;
            border-radius: 6px;
            text-decoration: none;
            font-weight: bold;
            margin: 20px 0;
        }

        .btn-add:hover {
            background: #45a049;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            border-radius: 10px;
            overflow: hidden;
            margin-top: 10px;
        }

        th, td {
            padding: 12px 16px;
            text-align: center;
            border-bottom: 1px solid #eee;
            vertical-align: top;
        }

        th {
            background: #2f3640;
            color: white;
        }

        tr:hover {
            background: #e8f5e9;
        }

        .product-item {
            text-align: left;
            padding-left: 10px;
        }

        .message-success {
            background: #d4edda;
            color: #155724;
            padding: 10px;
            border-radius: 5px;
            margin: 10px 0;
        }

        .message-error {
            background: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 5px;
            margin: 10px 0;
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

<h2>Danh sách đơn gọi món</h2>
<a href="order_list?action=create" class="btn-add">➕ Thêm đơn mới</a>

<c:if test="${not empty param.success}">
    <div class="message-success">✔️ Thao tác thành công</div>
</c:if>
<c:if test="${not empty param.error}">
    <div class="message-error">❌ Lỗi: ${param.error}</div>
</c:if>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Bàn</th>
        <th>Thời gian</th>
        <th>Người gọi</th>
        <th>Ghi chú</th>
        <th>Sản phẩm</th>
        <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.id}</td>
            <td>${order.table.name}</td>
            <td>
                <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm:ss"/>
            </td>
            <td>${order.user.fullname}</td>
            <td>${order.note}</td>
            <td>
                <c:forEach var="detail" items="${order.orderDetails}">
                    <div class="product-item">
                            ${detail.product.name}<br/>
                        SL: ${detail.quantity} -
                        <fmt:formatNumber value="${detail.price}" type="currency"/>
                    </div>
                </c:forEach>
            </td>
            <td>
                <a class="action-link" href="order_list?action=edit&id=${order.id}">Sửa</a> |
                <a class="action-link" href="order_list?action=delete&id=${order.id}"
                   onclick="return confirm('Xóa đơn này?');">Xóa</a>
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
