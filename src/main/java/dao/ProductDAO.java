package dao;

import javax.persistence.*;
import model.Product;
import java.util.List;

public class ProductDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");

    public Product findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public List<Product> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void save(Product product) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            if (product.getId() == 0) {
                em.persist(product); // insert mới
            } else {
                em.merge(product);   // update
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Product product = em.find(Product.class, id);
            if (product != null) {
                em.remove(product); // tự động xóa các MenuItem liên kết (do cascade = REMOVE)
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Product findByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Product> products = em.createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class)
                    .setParameter("name", name)
                    .getResultList();

            return products.isEmpty() ? null : products.get(0);

        } finally {
            em.close();
        }
    }

    public List<Product> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Product getById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Product.class, id);  // lấy Product theo id
        } finally {
            em.close();
        }
    }
}
