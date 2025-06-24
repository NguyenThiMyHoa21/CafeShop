package Servlet;

import dao.OrderDAO;
import dao.ProductDAO;
import dao.MyTableDAO;
import dao.UserDAO;
import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet("/order_list")
public class OrderServlet extends HttpServlet {
    private OrderDAO orderDAO;
    private MyTableDAO tableDAO;
    private UserDAO userDAO;
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        tableDAO = new MyTableDAO();
        userDAO = new UserDAO();
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "create":
                showCreateForm(request, response);
                break;
            case "delete":
                deleteOrder(request, response);
                break;
            case "edit":
                showEditForm(request, response); // 👈 thêm dòng này
                break;

            default:
                listOrders(request, response);
                break;
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Order order = orderDAO.findById(id);
            if (order == null) {
                response.sendRedirect("order_list?error=Đơn không tồn tại");
                return;
            }

            request.setAttribute("order", order);
            request.setAttribute("tables", tableDAO.findAll());
            request.setAttribute("users", userDAO.findAll());
            request.setAttribute("products", productDAO.findAll());

            Map<Integer, Integer> selectedQuantities = new HashMap<>();
            for (OrderDetail detail : order.getOrderDetails()) {
                selectedQuantities.put(detail.getProduct().getId(), detail.getQuantity());
            }
            request.setAttribute("selectedQuantities", selectedQuantities);

            request.getRequestDispatcher("/WEB-INF/views/order_form.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("order_list?error=edit_failed");
        }
    }


    private void listOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Order> orders = orderDAO.getAllOrdersWithDetails();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/views/order_list.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("tables", tableDAO.findAll());
            request.setAttribute("users", userDAO.findAll());
            request.setAttribute("products", productDAO.findAll());
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Lỗi tải dữ liệu form: " + e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/views/order_form.jsp").forward(request, response);
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            orderDAO.delete(id);
            response.sendRedirect("order_list?success=deleted");
        } catch (Exception e) {
            response.sendRedirect("order_list?error=delete_failed");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if ("create".equals(action)) {
            createOrder(request, response);
        } else if ("update".equals(action)) {
            updateOrder(request, response);  // 👈 THÊM DÒNG NÀY
        }
    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("id"));
            Order order = orderDAO.findById(orderId);
            if (order == null) {
                response.sendRedirect("order_list?error=Đơn không tồn tại");
                return;
            }

            String note = request.getParameter("note");
            int userId = Integer.parseInt(request.getParameter("userId"));
            int tableId = Integer.parseInt(request.getParameter("tableId"));

            User user = userDAO.findById(userId);
            MyTable table = tableDAO.findById(tableId);

            order.setNote(note);
            order.setStatus("updated"); // hoặc lấy từ request nếu có
            order.setUser(user);
            order.setTable(table);
            order.setOrderTime(LocalDateTime.now());

            List<OrderDetail> newDetails = new ArrayList<>();
            BigDecimal total = BigDecimal.ZERO;

            // Lặp toàn bộ sản phẩm
            List<Product> products = productDAO.findAll();
            for (Product product : products) {
                String paramName = "quantities[" + product.getId() + "]";
                String quantityStr = request.getParameter(paramName);

                if (quantityStr != null && !quantityStr.isEmpty()) {
                    int quantity = Integer.parseInt(quantityStr);
                    if (quantity > 0) {
                        OrderDetail detail = new OrderDetail();
                        detail.setProduct(product);
                        detail.setQuantity(quantity);
                        detail.setPrice(product.getPrice());
                        detail.setOrder(order);
                        newDetails.add(detail);

                        total = total.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
                    }
                }
            }

            order.setOrderDetails(newDetails);
            order.setTotalAmount(total);
            orderDAO.update(order);

            response.sendRedirect("order_list?success=updated");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi khi cập nhật đơn hàng: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/order_form.jsp").forward(request, response);
        }
    }

    private void createOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String note = request.getParameter("note");
            int userId = Integer.parseInt(request.getParameter("userId"));
            int tableId = Integer.parseInt(request.getParameter("tableId"));

            User user = userDAO.findById(userId);
            MyTable table = tableDAO.findById(tableId);

            Order order = new Order();
            order.setNote(note);
            order.setStatus("pending");
            order.setUser(user);
            order.setTable(table);
            order.setOrderDate(new Date());
            order.setOrderTime(LocalDateTime.now());

            List<OrderDetail> orderDetails = new ArrayList<>();
            BigDecimal total = BigDecimal.ZERO;

            List<Product> products = productDAO.findAll();
            for (Product product : products) {
                String quantityStr = request.getParameter("quantities[" + product.getId() + "]");
                if (quantityStr != null && !quantityStr.isEmpty()) {
                    int quantity = Integer.parseInt(quantityStr);
                    if (quantity > 0) {
                        OrderDetail detail = new OrderDetail();
                        detail.setProduct(product);
                        detail.setQuantity(quantity);
                        detail.setPrice(product.getPrice());
                        detail.setOrder(order);
                        orderDetails.add(detail);

                        total = total.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
                    }
                }
            }

            order.setOrderDetails(orderDetails);
            order.setTotalAmount(total);
            orderDAO.save(order);
            response.sendRedirect("order_list?success=created");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi xảy ra khi tạo đơn hàng: " + e.getMessage());
            showCreateForm(request, response);
        }
    }
}