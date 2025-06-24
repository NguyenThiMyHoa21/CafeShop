<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thống kê doanh thu hôm nay</title>
    <style>
        body {
            font-family: Arial;
            background: #f9f9f9;
            padding: 30px;
        }
        .stat-box {
            background: white;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 0 8px rgba(0,0,0,0.1);
            width: 300px;
            margin: auto;
            text-align: center;
        }
        .stat-box h2 {
            margin: 0;
            font-size: 22px;
            color: #444;
        }
        .stat-box p {
            font-size: 20px;
            color: green;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="stat-box">
    <h2>📊 Doanh thu hôm nay</h2>
    <p><strong>Số hóa đơn:</strong> ${todayOrderCount}</p>
    <p><strong>Tổng doanh thu:</strong> <fmt:formatNumber value="${todayRevenue}" type="currency" currencySymbol="₫" /></p>
    <p><a href="home.jsp">← Về trang chủ</a></p>
</div>
</body>
</html>
