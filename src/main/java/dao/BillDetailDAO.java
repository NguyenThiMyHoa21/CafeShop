package dao;

import model.BillDetail;
import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class BillDetailDAO {

    private EntityManager em;

    public BillDetailDAO(EntityManager em) {
        this.em = em;
    }

    public List<BillDetail> findByBillId(int billId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT bd FROM BillDetail bd JOIN FETCH bd.product WHERE bd.bill.id = :billId AND bd.quantity > 0",
                            BillDetail.class)
                    .setParameter("billId", billId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    public void save(BillDetail billDetail) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(billDetail);  // <-- Đây là nơi lưu vào DB
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
