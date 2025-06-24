package Servlet;

import model.Product;
import dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/product_form")
public class ProductFormServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO(); // KHÔNG truyền HibernateUtil nữa
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                Product product = productDAO.findById(id);
                if (product != null) {
                    request.setAttribute("product", product);
                } else {
                    request.setAttribute("error", "Không tìm thấy sản phẩm");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID không hợp lệ");
            }
        }

        request.getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(request, response);
    }
}
