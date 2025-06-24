package Servlet;

import service.MenuService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/delete_menu")
public class DeleteMenuServlet extends HttpServlet {

    private final MenuService menuService = new MenuService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            if (name != null && !name.trim().isEmpty()) {
                menuService.deleteByName(name);
            }

            // Chuyển về trang menu
            resp.sendRedirect(req.getContextPath() + "/menu");

        } catch (Exception e) {
            e.printStackTrace();
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("<h3 style='color:red;'>Lỗi khi xoá món: " + e.getMessage() + "</h3>");
        }
    }
}
