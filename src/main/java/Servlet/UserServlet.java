package Servlet;

import dao.RoleDAO;
import dao.UserDAO;
import model.Role;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/user-create")
public class UserServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();
    private RoleDAO roleDAO = new RoleDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String roleName = request.getParameter("role"); // VD: "admin", "cashier", "staff"

        // Tìm role theo tên
        Role role = roleDAO.findByName(roleName);
        if (role == null) {
            request.setAttribute("error", "Role không hợp lệ");
            request.getRequestDispatcher("/WEB-INF/views/user_form.jsp").forward(request, response);
            return;
        }

        // Tạo user mới đúng cách
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // 👉 Nên dùng BCrypt để mã hóa
        user.setFullname(fullname);
        user.setRole(role);

        // Lưu user
        try {
            userDAO.save(user);
            response.sendRedirect(request.getContextPath() + "/user-list");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi lưu user");
            request.getRequestDispatcher("/user_form.jsp").forward(request, response);
        }
    }
}
