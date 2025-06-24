<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý bàn</title>
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

        .status-trong {
            background: #8dc63f;
            color: white;
            padding: 5px 10px;
            border-radius: 6px;
        }

        .status-dangdung {
            background: #f0ad4e;
            color: white;
            padding: 5px 10px;
            border-radius: 6px;
        }

        .status-dadat {
            background: #d9534f;
            color: white;
            padding: 5px 10px;
            border-radius: 6px;
        }

        .action-link {
            color: #c0392b;
            text-decoration: none;
            font-weight: bold;
        }

        .action-link:hover {
            text-decoration: underline;
        }

        .back-btn {
            margin-top: 30px;
            text-align: center;
        }

        .back-btn a {
            text-decoration: none;
            color: #2980b9;
            font-weight: bold;
        }

        .back-btn a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<h2>Danh sách bàn</h2>
<a href="showAddTableForm" class="btn-add">Thêm bàn mới</a>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Tên bàn</th>
        <th>Trạng thái</th>
        <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${not empty tables}">
            <c:forEach var="table" items="${tables}">
                <tr>
                    <td>${table.id}</td>
                    <td>${table.name}</td>
                    <td class="${table.status == 'Trống' ? 'status-trong' : (table.status == 'Đang dùng' ? 'status-dangdung' : 'status-dadat')}">
                            ${table.status}
                    </td>
                    <td>
                        <a class="action-link" href="delete_table?id=${table.id}" onclick="return confirm('Bạn có chắc muốn xóa bàn này?');">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="4">Không có dữ liệu bàn để hiển thị.</td>
            </tr>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>

<div class="back-btn">
    <a href="home">← Quay về danh sách hóa đơn</a>
</div>

</body>
</html>
