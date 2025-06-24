package Servlet;

import dao.MyTableDAO;
import model.MyTable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/add_table")
public class AddTableServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MyTableDAO tableDAO;

    @Override
    public void init() throws ServletException {
        tableDAO = new MyTableDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển tới trang form thêm bàn
        request.getRequestDispatcher("/WEB-INF/views/add_table.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String name = req.getParameter("name");
        String status = req.getParameter("status");

        // Tạo đối tượng MyTable mới
        MyTable table = new MyTable();
        table.setName(name);
        table.setStatus(status);

        // Gọi DAO để lưu vào CSDL
        tableDAO.insert(table);

        // Chuyển hướng về danh sách bàn sau khi thêm thành công
        resp.sendRedirect("table_list"); // đúng URL mapping danh sách bàn
    }
}