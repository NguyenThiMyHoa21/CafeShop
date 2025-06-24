package Servlet;

import dao.MenuItemDAO;
import dao.ProductDAO;
import model.MenuItem;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

    private MenuItemDAO menuItemDAO;
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        menuItemDAO = new MenuItemDAO();
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<MenuItem> menuItems = menuItemDAO.getAll();

        // Không cần set price và imagePath nữa nếu truy cập từ item.getProduct()
        request.setAttribute("menu", menuItems);
        request.getRequestDispatcher("/WEB-INF/views/menu.jsp").forward(request, response);
    }
}
