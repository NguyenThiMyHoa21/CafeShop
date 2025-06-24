package Servlet;

import dao.*;
import model.*;
import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet("/add_bill")
public class AddBillServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<MyTable> tables = em.createQuery("SELECT t FROM MyTable t", MyTable.class).getResultList();
            List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
            List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();

            request.setAttribute("tables", tables);
            request.setAttribute("users", users);
            request.setAttribute("products", products);

            request.getRequestDispatcher("/WEB-INF/views/add_bill.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            // Lấy dữ liệu từ form
            int tableId = Integer.parseInt(request.getParameter("tableId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            String[] productIds = request.getParameterValues("productId");
            String[] quantities = request.getParameterValues("quantity");

            if (productIds == null || quantities == null) {
                throw new ServletException("Không có sản phẩm nào được chọn.");
            }


            // Bắt đầu transaction
            tx.begin();

            // Lấy đối tượng bàn và người dùng
            MyTable table = em.find(MyTable.class, tableId);
            User user = em.find(User.class, userId);

            // Tạo hóa đơn
            Bill bill = new Bill();
            bill.setTable(table);
            bill.setUser(user);
            bill.setCreatedAt(LocalDateTime.now()); // Quan trọng!
            bill.setTotal(BigDecimal.ZERO); // Tạm thời, sẽ cập nhật sau
            em.persist(bill); // Phải persist trước khi thêm BillDetail

            BigDecimal total = BigDecimal.ZERO;

            // Duyệt từng món để tạo BillDetail
            for (int i = 0; i < productIds.length; i++) {
                if (productIds[i] == null || productIds[i].isEmpty() ||
                        quantities[i] == null || quantities[i].isEmpty()) {
                    continue;
                }

                int productId = Integer.parseInt(productIds[i]);
                int quantity = Integer.parseInt(quantities[i]);

                if (quantity <= 0) continue; // ⚠️ Ngăn không lưu sp số lượng 0

                Product product = em.find(Product.class, productId);
                BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
                total = total.add(itemTotal);

                BillDetail detail = new BillDetail();
                detail.setBill(bill);
                detail.setProduct(product);
                detail.setQuantity(quantity);
                detail.setUnitPrice(product.getPrice()); // ✅ phải set trường unitPrice
                em.persist(detail);
            }

            // Cập nhật tổng tiền cho hóa đơn
            bill.setTotal(total);
            em.merge(bill);

            tx.commit();

            response.sendRedirect("bill_list"); // Điều hướng sau khi thêm thành công
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            throw new ServletException("Lỗi khi thêm hóa đơn", e);
        } finally {
            em.close();
        }
    }
}
