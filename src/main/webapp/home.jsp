<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String userRole = (String) session.getAttribute("userRole");
    String userFullname = (String) session.getAttribute("userFullname");
    if (userRole == null || userFullname == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    request.setAttribute("userRole", userRole);
    request.setAttribute("userFullname", userFullname);
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang chủ</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <style>
        * { box-sizing: border-box; }
        body {
            margin: 0;
            font-family: 'Roboto', sans-serif;
            display: flex;
            min-height: 100vh;
        }

        .sidebar {
            width: 220px;
            background-color: #2f3640;
            color: #fff;
            padding: 20px 0;
        }

        .sidebar h2 {
            text-align: center;
            margin-bottom: 30px;
        }

        .sidebar a {
            display: block;
            color: white;
            text-decoration: none;
            padding: 12px 20px;
            transition: background 0.3s;
        }

        .sidebar a:hover {
            background-color: #353b48;
        }

        .content {
            flex: 1;
            background: #f5f6fa;
            padding: 30px;
        }

        h1 {
            color: #2c3e50;
            margin-bottom: 30px;
        }

        .user-info {
            text-align: center;
            margin-top: 40px;
            color: #bdc3c7;
            font-size: 14px;
        }

        .stat-box {
            background: #eafaf1;
            border-left: 5px solid #2ecc71;
            padding: 20px 30px;
            margin-bottom: 30px;
            border-radius: 10px;
        }

        .stat-box h2 {
            color: #2c3e50;
            margin-bottom: 15px;
        }

        .stat-box p {
            font-size: 18px;
            margin: 5px 0;
        }
    </style>
</head>
<body>
<div class="sidebar">
    <h2>☕ CafeShop</h2>
    <!--<a href="home.jsp">🏠 Trang chủ</a>-->
    <a href="home">🏠 Trang chủ</a>
    <a href="order_list">📋 Quản lý Đơn hàng</a>
    <a href="user_list">👥 Quản lý Tài khoản</a>
    <a href="table_list">🍽️ Quản lý Bàn</a>
    <a href="product_list">🛒 Quản lý Sản phẩm</a>
    <a href="menu">📖 Quản lý Menu</a>
    <a href="bill_list">🧾 Xem Hóa đơn</a>
    <a href="logout">🚪 Đăng xuất</a>

    <div class="user-info">
        Xin chào, <strong>${userFullname}</strong><br>
        Vai trò: ${userRole}
    </div>
</div>

<div class="content">
    <div class="stat-box">
        <h2>📈 Thống kê hôm nay</h2>
        <p><strong>Số hóa đơn:</strong> ${ordersToday}</p>
        <p>
            <strong>Doanh thu:</strong>
            <fmt:formatNumber value="${revenueToday}" type="currency" currencySymbol="₫" />
        </p>
    </div>

    <div style="text-align: center; margin-bottom: 30px;">
        <img src="images/menu/anh.jpg" alt="Ảnh chào mừng"
             style="width: 1045px; height: 580px; border-radius: 10px">
    </div>
</div>
</body>
</html>
