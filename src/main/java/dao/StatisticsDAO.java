package dao;

import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class StatisticsDAO {

    // Tổng doanh thu trong ngày
    public BigDecimal getTodayRevenue() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            LocalDate today = LocalDate.now();
            LocalDateTime startOfDay = today.atStartOfDay();
            LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

            Query query = em.createQuery("SELECT COALESCE(SUM(b.total), 0) FROM Bill b WHERE b.createdAt BETWEEN :start AND :end");
            query.setParameter("start", startOfDay);
            query.setParameter("end", endOfDay);

            return (BigDecimal) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    // Tổng số đơn hàng trong ngày
    public long getTodayOrderCount() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            LocalDate today = LocalDate.now();
            LocalDateTime startOfDay = today.atStartOfDay();
            LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

            Query query = em.createQuery("SELECT COUNT(b.id) FROM Bill b WHERE b.createdAt BETWEEN :start AND :end");
            query.setParameter("start", startOfDay);
            query.setParameter("end", endOfDay);

            return (long) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public long countOrdersToday() {
        return getTodayOrderCount();
    }

    public double totalRevenueToday() {
        return getTodayRevenue().doubleValue();
    }

}
