<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8" />
    <title>Menu hệ thống</title>
    <style>
        body {
            margin: 0;
            font-family: "Segoe UI", sans-serif;
            background: #f5f6fa;
            padding: 30px;
        }

        h2 {
            color: #2f3640;
            text-align: center;
            margin-bottom: 30px;
        }

        ul.dynamic-menu {
            list-style: none;
            padding: 0;
            max-width: 1000px;
            margin: 0 auto 40px auto;
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            gap: 20px;
        }

        ul.dynamic-menu li {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            overflow: hidden;
        }

        ul.dynamic-menu li img {
            width: 100%;
            height: 140px;
            object-fit: cover;
        }

        ul.dynamic-menu li .menu-info {
            padding: 10px;
        }

        .menu-name {
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-weight: bold;
            font-size: 16px;
            margin-bottom: 6px;
        }

        .menu-price {
            color: gray;
            font-size: 14px;
        }

        .menu-delete {
            background: none;
            border: none;
            font-size: 16px;
            color: red;
            cursor: pointer;
        }

        .menu-delete:hover {
            text-decoration: underline;
        }

        .menu-link {
            text-decoration: none;
            color: inherit;
            display: block;
        }

        .menu-link:hover {
            background: #f0f0f0;
        }
    </style>
</head>
<body>

<h2>📋 Menu hệ thống</h2>

<ul class="dynamic-menu">
    <c:forEach var="item" items="${menu}">
        <c:if test="${not empty item.product}">
            <li>
                <a href="${item.url}" class="menu-link">
                    <img src="${pageContext.request.contextPath}/images/menu/${item.product.imagePath}" alt="${item.name}" />
                    <div class="menu-info">
                        <div class="menu-name">
                            <span>${item.name}</span>
                            <form action="${pageContext.request.contextPath}/delete_menu" method="post" style="margin: 0;">
                                <input type="hidden" name="name" value="${item.name}" />
                                <button type="submit" class="menu-delete" title="Xóa món"
                                        onclick="return confirm('Bạn có chắc muốn xóa món này?')">🗑️</button>
                            </form>
                        </div>
                        <div class="menu-price">
                            <c:if test="${not empty item.product.price}">
                                ${item.product.price} VNĐ
                            </c:if>
                        </div>
                    </div>
                </a>
            </li>
        </c:if>
    </c:forEach>
</ul>
<div class="back-btn">
    <a href="home">← Quay về trang chủ</a>
</div>
</body>
</html>
