package dao;

import model.Order;
import model.OrderDetail;
import util.JPAUtil;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;

public class OrderDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");

    // Thêm mới đơn hàng
    public void save(Order order) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(order);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Cập nhật đơn hàng
    public void update(Order order) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(order);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Xóa đơn hàng
    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Order order = em.find(Order.class, id);
            if (order != null) {
                em.remove(order);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Tìm đơn hàng theo ID (tên method khác)
    public Order findById(int orderId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Order.class, orderId);
        } finally {
            em.close();
        }
    }

    // Trong OrderDAO.java
    public List<Order> getAllOrdersWithDetails() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT DISTINCT o FROM Order o " +
                                    "LEFT JOIN FETCH o.orderDetails d " +
                                    "LEFT JOIN FETCH d.product " +
                                    "LEFT JOIN FETCH o.table " +
                                    "LEFT JOIN FETCH o.user", Order.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }


    // Đóng EntityManagerFactory
    public void close() {
        emf.close();
    }

    public List<Order> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
        } finally {
            em.close();
        }
    }
}
