package Servlet;

import dao.ProductDAO;
import model.MenuItem;
import model.Product;
import service.MenuService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

@WebServlet("/edit_product")
public class EditProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            // Lấy dữ liệu từ form
            String idStr = request.getParameter("id");
            String name = request.getParameter("name");
            String priceStr = request.getParameter("price");
            String category = request.getParameter("category");
            String imagePath = request.getParameter("imagePath");

            int id = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : 0;
            BigDecimal price = new BigDecimal(priceStr);

            // Tạo đối tượng Product
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setPrice(price);
            product.setCategory(category);
            product.setImagePath(imagePath);

            // Lưu sản phẩm
            ProductDAO dao = new ProductDAO();
            dao.save(product);

            // Nếu là sản phẩm mới → thêm vào menu
            if (id == 0) {
                MenuItem item = new MenuItem(product, "product_list", Arrays.asList("ADMIN", "STAFF"));
                MenuService menuService = new MenuService();
                menuService.save(item);
            }

            // Chuyển hướng về danh sách sản phẩm
            response.sendRedirect(request.getContextPath() + "/product_list");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi lưu sản phẩm.");
            request.getRequestDispatcher("/WEB-INF/views/product_form.jsp").forward(request, response);
        }
    }
}
