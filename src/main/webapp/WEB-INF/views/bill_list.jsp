<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách hóa đơn</title>
    <style>
        body { margin: 0; font-family: "Segoe UI", sans-serif; display: flex; }
        .sidebar { width: 220px; background: #2f3640; color: white; padding: 20px 0; }
        .content { flex: 1; padding: 30px; background: #f5f6fa; }
        h2 { text-align: center; color: #2f3640; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; border-radius: 10px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
        th, td { padding: 12px; text-align: center; border-bottom: 1px solid #eee; }
        th { background: #2f3640; color: white; }
        tr:nth-child(even) { background: #f9f9f9; }
    </style>
</head>
<body>


<div class="content">
    <h2>Danh sách hóa đơn</h2>

    <div style="text-align: right; margin-bottom: 10px;">
        <a href="add_bill" style="
        background-color: #27ae60;
        color: white;
        padding: 10px 16px;
        text-decoration: none;
        border-radius: 5px;
        font-weight: bold;
    ">Thêm hóa đơn</a>
    </div>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Đơn hàng</th>
            <th>Tổng tiền</th>
            <th>Ngày tạo</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="bill" items="${billList}">
            <tr>
                <td>${bill.id}</td>
                <td>${bill.table.name}</td>
                <td>${bill.total}</td>
                <td>${bill.createdAt}</td>
                <td>
                    <a href="bill_detail?id=${bill.id}">Chi tiết</a> |
                    <a href="delete_bill?id=${bill.id}" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="back-btn">
        <a href="home">← Quay về trang chủ</a>
    </div>
</div>

</body>
</html>
