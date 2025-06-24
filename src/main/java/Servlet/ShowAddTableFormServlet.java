package Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/showAddTableForm")
public class ShowAddTableFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Chuyển tiếp yêu cầu đến trang JSP hiển thị form thêm bàn
        req.getRequestDispatcher("/WEB-INF/views/add_table.jsp").forward(req, resp);
    }
}
