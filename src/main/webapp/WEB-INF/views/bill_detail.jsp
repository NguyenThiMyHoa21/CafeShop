<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết hóa đơn</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f9f9f9;
        }
        h2 {
            color: #333;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            background: #fff;
            box-shadow: 0 0 5px rgba(0,0,0,0.1);
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px 15px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
            color: #444;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .info, .total {
            margin: 20px 0;
            font-size: 16px;
        }
        .back-btn {
            margin-top: 20px;
        }
        .back-btn a {
            text-decoration: none;
            color: white;
            background: #28a745;
            padding: 10px 20px;
            border-radius: 5px;
        }
        .back-btn a:hover {
            background: #218838;
        }
    </style>
</head>
<body>

<h2>Chi tiết hóa đơn</h2>

<c:if test="${empty bill}">
    <p style="color: red;">Không tìm thấy hóa đơn.</p>
</c:if>

<c:if test="${not empty bill}">
    <div class="info">
        <p><strong>Mã hóa đơn:</strong> ${bill.id}</p>
        <p><strong>Nhân viên:</strong> ${bill.user.fullname}</p>
        <p><strong>Bàn:</strong> ${bill.table.name}</p>
        <p><strong>Thời gian:</strong> ${bill.createdAt}</p>
    </div>

    <table border="1">
        <thead>
        <tr>
            <th>#</th>
            <th>Sản phẩm</th>
            <th>Số lượng</th>
            <th>Đơn giá (VNĐ)</th>
            <th>Thành tiền (VNĐ)</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${details}" varStatus="loop">
            <c:if test="${item.quantity > 0}">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${item.product.name}</td>
                    <td>${item.quantity}</td>
                    <td>${item.unitPrice}</td>
                    <td>${item.price}</td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>

    <div class="total">
        <p><strong>Tổng tiền:</strong> ${bill.total} VNĐ</p>
    </div>
</c:if>

<div class="back-btn">
    <a href="bill_list">← Quay về danh sách hóa đơn</a>
</div>

</body>
</html>
