package dao;

import model.MyTable;

import javax.persistence.*;
import java.util.List;

public class MyTableDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("myPersistenceUnit");

    public List<MyTable> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT t FROM MyTable t", MyTable.class).getResultList();
        } finally {
            em.close();
        }
    }

    public MyTable findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(MyTable.class, id);
        } finally {
            em.close();
        }
    }

    public void insert(MyTable table) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(table);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void update(MyTable table) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(table);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            MyTable table = em.find(MyTable.class, id);
            if (table != null) {
                em.remove(table);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<MyTable> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT t FROM MyTable t", MyTable.class).getResultList();
        } finally {
            em.close();
        }
    }

    public MyTable getById(int tableId) {
        EntityManager em = emf.createEntityManager();
        MyTable table = null;

        try {
            table = em.find(MyTable.class, tableId);
        } finally {
            em.close();
        }

        return table;
    }
}
