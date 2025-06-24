package Servlet;

import dao.MyTableDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

    @WebServlet("/delete_table")
    public class DeleteTableServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {

            String idStr = req.getParameter("id");

            if (idStr != null) {
                try {
                    int id = Integer.parseInt(idStr);
                    MyTableDAO dao = new MyTableDAO();
                    dao.delete(id);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            resp.sendRedirect(req.getContextPath() + "/table_list");
        }
    }
