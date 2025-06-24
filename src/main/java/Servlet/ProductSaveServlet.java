package Servlet;

import dao.ProductDAO;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/save_product")
public class ProductSaveServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action"); // "delete" hoặc null
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String category = request.getParameter("category");
        String imagePath = request.getParameter("imagePath");

        try {
            // XÓA sản phẩm
            if ("delete".equalsIgnoreCase(action)) {
                if (idStr != null && !idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    productDAO.delete(id);
                }
                response.sendRedirect(request.getContextPath() + "/product_list");
                return;
            }

            // Validate dữ liệu nhập
            if (name == null || name.trim().isEmpty() ||
                    priceStr == null || priceStr.trim().isEmpty() ||
                    category == null || category.trim().isEmpty()) {

                Product invalidProduct = new Product(name, BigDecimal.ZERO, category, imagePath);
                request.setAttribute("product", invalidProduct);
                request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin.");
                request.getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(request, response);
                return;
            }

            BigDecimal price = new BigDecimal(priceStr);
            Product product;

            if (idStr != null && !idStr.isEmpty()) {
                // CẬP NHẬT
                int id = Integer.parseInt(idStr);
                product = productDAO.findById(id);
                if (product != null) {
                    product.setName(name);
                    product.setPrice(price);
                    product.setCategory(category);
                    product.setImagePath(imagePath);
                    productDAO.save(product);
                }
            } else {
                // THÊM MỚI
                product = new Product(name, price, category, imagePath);
                productDAO.save(product);
            }

            response.sendRedirect(request.getContextPath() + "/product_list");

        } catch (NumberFormatException e) {
            Product fallbackProduct = new Product(name, BigDecimal.ZERO, category, imagePath);
            request.setAttribute("product", fallbackProduct);
            request.setAttribute("error", "Giá sản phẩm không hợp lệ.");
            request.getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            Product fallbackProduct = new Product(name, BigDecimal.ZERO, category, imagePath);
            request.setAttribute("product", fallbackProduct);
            request.setAttribute("error", "Đã xảy ra lỗi khi xử lý sản phẩm.");
            request.getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(request, response);
        }
    }
}
