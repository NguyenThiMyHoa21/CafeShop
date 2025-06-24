package Servlet;

import dao.MyTableDAO;
import model.MyTable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/table_list")
public class TableServlet extends HttpServlet {

    private MyTableDAO tableDAO = new MyTableDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<MyTable> tables = tableDAO.findAll();
        req.setAttribute("tables", tables);
        req.getRequestDispatcher("/WEB-INF/views/table_list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");
        String name = req.getParameter("name");
        String status = req.getParameter("status");

        if (idStr == null || idStr.isEmpty()) {
            // Thêm mới
            MyTable table = new MyTable();
            table.setName(name);
            table.setStatus(status);
            tableDAO.insert(table);
        } else {
            // Cập nhật
            int id = Integer.parseInt(idStr);
            MyTable table = tableDAO.findById(id);
            if (table == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            table.setName(name);
            table.setStatus(status);
            tableDAO.update(table);
        }

        resp.sendRedirect(req.getContextPath() + "/table_list");
    }
}

