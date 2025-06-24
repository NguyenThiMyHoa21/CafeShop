package service;

import model.Bill;
import model.BillDetail;
import model.MyTable;
import model.Product;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderProcessingService {

    private final EntityManagerFactory emf;

    public OrderProcessingService(EntityManagerFactory emf) {
        if (emf == null) {
            throw new IllegalArgumentException("EntityManagerFactory không được null");
        }
        this.emf = emf;
    }

    /**
     * Tạo mới hóa đơn và chi tiết hóa đơn trong một transaction duy nhất.
     *
     * @param table bàn được chọn
     * @param user  nhân viên tạo hóa đơn
     * @param items danh sách món ăn và số lượng
     * @return hóa đơn đã tạo
     */
    public Bill createOrder(MyTable table, User user, List<OrderItem> items) {
        if (table == null || user == null || items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Dữ liệu đầu vào không hợp lệ");
        }

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // 1. Tạo đối tượng Bill
            Bill bill = new Bill();
            bill.setTable(table);
            bill.setUser(user);
            bill.setCreatedAt(LocalDateTime.now());
            bill.setStatus("Chưa thanh toán");

            // 2. Tính tổng tiền hóa đơn
            BigDecimal total = BigDecimal.ZERO;
            for (OrderItem item : items) {
                BigDecimal itemTotal = item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));
                total = total.add(itemTotal);
            }
            bill.setTotal(total);

            // 3. Lưu Bill
            em.persist(bill);

            // 4. Tạo và lưu từng BillDetail
            for (OrderItem item : items) {
                BillDetail detail = new BillDetail();
                detail.setBill(bill);
                detail.setProduct(item.getProduct());
                detail.setQuantity(item.getQuantity());
                detail.setPrice(item.getProduct().getPrice()); // lưu giá tại thời điểm

                em.persist(detail);
            }

            tx.commit();
            return bill;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.err.println("❌ Lỗi khi tạo hóa đơn: " + e.getMessage());
            throw new RuntimeException("Không thể tạo hóa đơn", e);
        } finally {
            em.close();
        }
    }

    /**
     * Lớp lồng biểu diễn từng món + số lượng
     */
    public static class OrderItem {
        private final Product product;
        private final int quantity;

        public OrderItem(Product product, int quantity) {
            if (product == null || quantity <= 0) {
                throw new IllegalArgumentException("Sản phẩm hoặc số lượng không hợp lệ");
            }
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
