package dao;

import model.Bill;
import model.MyTable;
import model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class BillDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");

    private EntityManager em;

    public BillDAO(EntityManager em) {
        this.em = em;
    }
    public List<Bill> findAllBills() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT b FROM Bill b ORDER BY b.createdAt DESC", Bill.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public static Bill findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Bill.class, id);
        } finally {
            em.close();
        }
    }

    public void deleteBillById(int id) {
        Bill bill = em.find(Bill.class, id);
        if (bill != null) {
            em.remove(bill); // Nếu đã thiết lập cascade đúng thì BillDetail cũng bị xóa
        }
    }

    public boolean insert(Bill bill) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(bill);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean update(Bill bill) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(bill);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean delete(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            Bill bill = em.find(Bill.class, id);
            if (bill == null) return false;

            tx.begin();
            em.remove(bill);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public Bill findByIdWithDetails(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT b FROM Bill b " +
                                    "LEFT JOIN FETCH b.user " +
                                    "LEFT JOIN FETCH b.table " +
                                    "LEFT JOIN FETCH b.billDetails d " +
                                    "LEFT JOIN FETCH d.product " +
                                    "WHERE b.id = :id", Bill.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Bill> findByTable(MyTable table) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT b FROM Bill b WHERE b.table = :table ORDER BY b.createdAt DESC", Bill.class)
                    .setParameter("table", table)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Bill> findByUser(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT b FROM Bill b WHERE b.user = :user ORDER BY b.createdAt DESC", Bill.class)
                    .setParameter("user", user)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Bill> findByDateRange(LocalDateTime from, LocalDateTime to) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT b FROM Bill b WHERE b.createdAt BETWEEN :from AND :to ORDER BY b.createdAt DESC", Bill.class)
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
