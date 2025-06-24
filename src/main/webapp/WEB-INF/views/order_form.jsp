<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String userRole = (String) session.getAttribute("userRole");
    String userName = (String) session.getAttribute("userFullname");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm/Chỉnh sửa đơn hàng</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 30px;
        }

        h2 {
            text-align: center;
            color: #343a40;
        }

        form {
            max-width: 800px;
            margin: 0 auto;
            background-color: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        input[type="text"], textarea, select {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }

        textarea {
            resize: vertical;
        }

        .product-list {
            margin-bottom: 30px;
        }

        .product-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        .product-item label {
            flex: 1;
        }

        .product-item input {
            width: 80px;
            text-align: center;
        }

        .button-container {
            text-align: center;
        }

        .btn {
            background-color: #007bff;
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        .btn:hover {
            background-color: #0056b3;
        }

    </style>
</head>
<body>

<%-- Biến kiểm tra đang sửa hay thêm mới --%>
<c:set var="editing" value="${order != null}" />

<h2>${editing ? "Chỉnh sửa đơn hàng" : "Thêm đơn hàng mới"}</h2>

<c:if test="${editing}">
    <p style="text-align:center; color: #666;">
        Thời gian gọi món:
        <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm:ss"/>
    </p>
</c:if>

<form action="order_list" method="post">
    <input type="hidden" name="action" value="${editing ? "update" : "create"}" />
    <c:if test="${editing}">
        <input type="hidden" name="id" value="${order.id}" />
    </c:if>

    <label for="user">Nhân viên:</label>
    <select name="userId" id="user" required>
        <c:forEach var="user" items="${users}">
            <option value="${user.id}" ${editing && user.id == order.user.id ? 'selected' : ''}>
                    ${user.fullname}
            </option>
        </c:forEach>
    </select>

    <label for="table">Bàn:</label>
    <select name="tableId" id="table" required>
        <c:forEach var="table" items="${tables}">
            <option value="${table.id}" ${editing && table.id == order.myTable.id ? 'selected' : ''}>
                    ${table.name}
            </option>
        </c:forEach>
    </select>

    <div class="product-list">
        <h3>Chọn món và số lượng:</h3>
        <c:forEach var="product" items="${products}">
            <div class="product-item">
                <label>${product.name}</label>
                <input type="number" name="quantities[${product.id}]" min="0"
                       value="${selectedQuantities[product.id] != null ? selectedQuantities[product.id] : 0}" />
            </div>
        </c:forEach>
    </div>

    <label for="note">Ghi chú:</label>
    <textarea name="note" id="note" rows="3"><c:out value="${order.note}" default=""/></textarea>

    <div class="button-container">
        <button type="submit" class="btn">${editing ? "Cập nhật đơn hàng" : "Tạo đơn hàng"}</button>
    </div>
</form>

</body>
</html>