package Servlet;

import dao.MyTableDAO;
import dao.OrderDAO;
import dao.UserDAO;
import model.MyTable;
import model.Order;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/editOrder")
public class EditOrderServlet extends HttpServlet {
    private OrderDAO orderDAO;
    private MyTableDAO tableDAO;
    private UserDAO userDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
        tableDAO = new MyTableDAO();
        userDAO = new UserDAO();
    }

    // Hiển thị form chỉnh sửa đơn hàng
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            int orderId = Integer.parseInt(request.getParameter("id"));
            Order order = orderDAO.findById(orderId);

            if (order != null) {
                List<MyTable> tables = tableDAO.findAll();
                List<User> users = userDAO.findAll();

                request.setAttribute("order", order);
                request.setAttribute("tables", tables);
                request.setAttribute("users", users);

                request.getRequestDispatcher("/WEB-INF/views/order_form.jsp").forward(request, response);
            } else {
                response.sendRedirect("order_list");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("order_list");
        }
    }

    // Xử lý cập nhật đơn hàng
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            int orderId = Integer.parseInt(request.getParameter("id"));
            int tableId = Integer.parseInt(request.getParameter("tableId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            String status = request.getParameter("status");

            Order order = orderDAO.findById(orderId);
            if (order != null) {
                // Lấy table và user từ DB để gán vào đơn hàng
                MyTable table = tableDAO.findById(tableId);
                User user = userDAO.findById(userId);

                order.setTable(table);
                order.setUser(user);
                order.setStatus(status);

                orderDAO.update(order);
            }

            response.sendRedirect("order_list");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("order_list");
        }
    }
}
