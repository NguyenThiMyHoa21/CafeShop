package service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Bill;

public class BillService {

    private final EntityManagerFactory emf;

    public BillService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Tìm Bill theo id
     * @param id id của hóa đơn
     * @return Bill nếu tồn tại, null nếu không tìm thấy
     */
    public Bill getBillById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Bill.class, id);
        } finally {
            em.close();
        }
    }
}
