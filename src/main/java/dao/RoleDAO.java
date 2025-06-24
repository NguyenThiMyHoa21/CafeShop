package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import model.Role;
import util.JPAUtil;

import java.util.List;

public class RoleDAO {

    private EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public Role findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Role.class, id);
        } finally {
            em.close();
        }
    }

    public Role findByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // không tìm thấy trả về null
        } finally {
            em.close();
        }
    }

    public List<Role> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void save(Role role) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (role.getId() == 0) {
                em.persist(role);
            } else {
                em.merge(role);
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
            Role role = em.find(Role.class, id);
            if (role != null) {
                em.remove(role);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Role> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        } finally {
            em.close();
        }
    }

}
