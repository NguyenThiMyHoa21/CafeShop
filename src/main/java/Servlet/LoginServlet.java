package Servlet;

import dao.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO(); // DAO đã kết nối JPA
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userDAO.findByUsernameAndPassword(username, password);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("User: " + (user != null ? user.getFullname() : "null"));
        if (user != null) {
            System.out.println("Role: " + (user.getRole() != null ? user.getRole().getName() : "null"));
        }


        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // ✅ Chuẩn hóa vai trò để khớp với Filter
            String rawRole = user.getRole().getName().toLowerCase().trim();
            String normalizedRole;

            switch (rawRole) {
                case "admin":
                    normalizedRole = "admin";
                    break;
                case "thu ngân":
                case "thungan":
                case "thu_ngan":
                    normalizedRole = "thu_ngan";
                    break;
                case "phục vụ":
                case "phuc vu":
                case "phuc_vu":
                    normalizedRole = "phuc_vu";
                    break;
                default:
                    normalizedRole = "unknown";
            }
            session.setAttribute("userRole", normalizedRole);
            session.setAttribute("userFullname", user.getFullname());

            response.sendRedirect("home.jsp");
        } else {
            request.setAttribute("errorMessage", "Sai tên đăng nhập hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
