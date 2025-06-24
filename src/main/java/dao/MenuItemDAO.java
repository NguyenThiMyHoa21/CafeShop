package dao;

import model.MenuItem;
import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class MenuItemDAO {

    // Lấy tất cả menu kèm product (tránh lazy load lỗi ở JSP hoặc Servlet)
    public List<MenuItem> getAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT m FROM MenuItem m JOIN FETCH m.product", MenuItem.class
            ).getResultList();
        } finally {
            em.close();
        }
    }


    // Lấy menu theo vai trò (vai trò là String nằm trong List<String> roles)
    public List<MenuItem> getMenuByRole(String role) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT m FROM MenuItem m JOIN FETCH m.product WHERE :role MEMBER OF m.roles";
            TypedQuery<MenuItem> query = em.createQuery(jpql, MenuItem.class);
            query.setParameter("role", role);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    // Lưu menu mới
    public void save(MenuItem item) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(item);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    // Xoá menu theo tên
    public void deleteByName(String name) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            List<MenuItem> items = em.createQuery("SELECT m FROM MenuItem m WHERE m.name = :name", MenuItem.class)
                    .setParameter("name", name)
                    .getResultList();
            for (MenuItem item : items) {
                em.remove(em.contains(item) ? item : em.merge(item));
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    // Xoá menu theo ID
    public void deleteById(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            MenuItem item = em.find(MenuItem.class, id);
            if (item != null) {
                em.remove(item);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
}
