package service;

import model.MenuItem;

import javax.persistence.*;
import java.util.List;

public class MenuService {

    private EntityManagerFactory emf;

    public MenuService() {
        emf = Persistence.createEntityManagerFactory("myPersistenceUnit"); // ✅ Đảm bảo tên đúng trong persistence.xml
    }

    public void save(MenuItem item) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(item);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // ✅ DÙNG native SQL thay vì HQL để truy vấn roles (do @ElementCollection không dùng MEMBER OF được)
    public List<MenuItem> getMenuItemsByRole(String role) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT m FROM MenuItem m WHERE :role MEMBER OF m.roles", MenuItem.class)
                    .setParameter("role", role)
                    .getResultList();
        } finally {
            em.close();
        }
    }


    // ✅ Lấy tất cả menu item
    public List<MenuItem> getAllMenuItems() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT m FROM MenuItem m", MenuItem.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ✅ Xoá theo tên món
    public void deleteByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM MenuItem m WHERE m.name = :name");
            query.setParameter("name", name);
            query.executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
