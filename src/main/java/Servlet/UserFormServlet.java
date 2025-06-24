package Servlet;

import model.Role;
import model.User;

import dao.RoleDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/showAddUserForm")
public class UserFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy danh sách vai trò để hiển thị trong form
        RoleDAO roleDAO = new RoleDAO();
        List<Role> roles = roleDAO.getAll();
        request.setAttribute("roles", roles);

        // Chuyển đến form thêm user
        request.getRequestDispatcher("/WEB-INF/views/add_user.jsp").forward(request, response);
    }
}
