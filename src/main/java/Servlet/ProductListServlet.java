package Servlet;

import dao.ProductDAO;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/product_list")
public class ProductListServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if (action == null) {
                listProducts(req, resp);
            } else {
                switch (action) {
                    case "showAddForm":
                        req.getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(req, resp);
                        break;

                    case "edit":
                        int id = Integer.parseInt(req.getParameter("id"));
                        Product product = productDAO.findById(id);
                        req.setAttribute("product", product);
                        req.getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(req, resp);
                        break;

                    case "delete":
                        int deleteId = Integer.parseInt(req.getParameter("id"));
                        productDAO.delete(deleteId);
                        resp.sendRedirect(req.getContextPath() + "/product_list");
                        break;

                    default:
                        listProducts(req, resp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi xử lý hành động");
            listProducts(req, resp);
        }
    }

    private void listProducts(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> productList = productDAO.findAll();
        req.setAttribute("productList", productList);
        req.getRequestDispatcher("/WEB-INF/views/product_list.jsp").forward(req, resp);
    }
}
