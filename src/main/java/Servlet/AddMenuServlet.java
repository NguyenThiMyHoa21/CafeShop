package Servlet;

import model.MenuItem;
import model.Product;
import service.MenuService;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/addMenu")
public class AddMenuServlet extends HttpServlet {

    private MenuService menuService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        menuService = new MenuService();
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy danh sách sản phẩm để hiển thị trong form
        List<Product> productList = productService.findAll();
        req.setAttribute("products", productList);
        req.getRequestDispatcher("/WEB-INF/views/add_menu.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getParameter("url");
        String rolesStr = req.getParameter("roles");
        int productId = Integer.parseInt(req.getParameter("productId"));

        List<String> roles = (rolesStr != null && !rolesStr.trim().isEmpty())
                ? Arrays.asList(rolesStr.trim().split("\\s*,\\s*"))
                : null;

        Product product = productService.findById(productId);

        if (product != null) {
            MenuItem item = new MenuItem();
            item.setProduct(product);
            item.setUrl(url);
            item.setName(product.getName()); // dùng tên món
            item.setRoles(roles);            // roles từ form

            menuService.save(item);
        }


        resp.sendRedirect("menu");
    }
}
