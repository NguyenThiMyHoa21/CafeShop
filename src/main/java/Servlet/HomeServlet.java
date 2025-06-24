package Servlet;

import dao.StatisticsDAO;
import model.MenuItem;
import service.MenuService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private MenuService menuService;
    private StatisticsDAO statisticsDAO;

    @Override
    public void init() throws ServletException {
        menuService = new MenuService();
        statisticsDAO = new StatisticsDAO();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra phiên đăng nhập
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy vai trò từ session
        String role = (String) session.getAttribute("userRole");
        if (role == null || role.isEmpty()) {
            response.sendRedirect("login.jsp"); // hoặc chuyển hướng đến trang lỗi phân quyền
            return;
        }

        // Lấy danh sách menu phù hợp vai trò
        List<MenuItem> menuItems = menuService.getMenuItemsByRole(role);

        long ordersToday = statisticsDAO.countOrdersToday();
        double revenueToday = statisticsDAO.totalRevenueToday();
        request.setAttribute("ordersToday", ordersToday);
        request.setAttribute("revenueToday", revenueToday);

        // Gửi dữ liệu sang JSP
        request.setAttribute("menu", menuItems);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        if (menuService != null) {
            menuService.close();
        }
    }
}
