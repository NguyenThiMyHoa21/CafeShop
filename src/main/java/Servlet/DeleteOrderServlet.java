package Servlet;

import dao.OrderDAO;
import model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/deleteOrder")
public class DeleteOrderServlet extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            int orderId = Integer.parseInt(request.getParameter("id"));

            Order order = orderDAO.findById(orderId);
            if (order != null) {
                orderDAO.delete(orderId);
                // Xóa thành công, chuyển hướng
                response.sendRedirect("order_list.jsp?success=true");
            } else {
                // Không tìm thấy đơn hàng
                response.sendRedirect("order_list.jsp?error=notfound");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Gặp lỗi khi xử lý
            response.sendRedirect("order_list.jsp?error=exception");
        }
    }
}
