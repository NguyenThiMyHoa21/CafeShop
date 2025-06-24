<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm hóa đơn</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h2 {
            text-align: center;
        }
        form {
            max-width: 900px;
            margin: auto;
            background-color: #f7f7f7;
            padding: 20px;
            border-radius: 8px;
        }
        label {
            font-weight: bold;
        }
        select, input[type="number"] {
            padding: 5px;
            margin: 5px 0 15px 0;
            width: 100%;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }
        table th, table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }
        table th {
            background-color: #f0f0f0;
        }
        .submit-btn {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #3498db;
            color: white;
            font-weight: bold;
            border: none;
            border-radius: 5px;
            margin-top: 20px;
        }
        .submit-btn:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
<h2>Thêm hóa đơn mới</h2>

<form action="add_bill" method="post">
    <label for="tableId">Chọn bàn:</label>
    <select name="tableId" id="tableId" required>
        <option value="">-- Chọn bàn --</option>
        <c:forEach var="table" items="${tables}">
            <option value="${table.id}">${table.name}</option>
        </c:forEach>
    </select>

    <label for="userId">Chọn nhân viên lập hóa đơn:</label>
    <select name="userId" id="userId" required>
        <option value="">-- Chọn nhân viên --</option>
        <c:forEach var="user" items="${users}">
            <option value="${user.id}">${user.fullname}</option>
        </c:forEach>
    </select>

    <label>Chọn sản phẩm và số lượng:</label>
    <table>
        <thead>
        <tr>
            <th>Sản phẩm</th>
            <th>Giá</th>
            <th>Số lượng</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <input type="hidden" name="productId" value="${product.id}" />
                        ${product.name}
                </td>
                <td>${product.price} đ</td>
                <td>
                    <input type="number" name="quantity" min="0" value="0" />
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="back">
        <a href="bill_list">⬅ Trở về</a>
    </div>
    <button type="submit" class="submit-btn">Lưu hóa đơn</button>
</form>

</body>
</html>
