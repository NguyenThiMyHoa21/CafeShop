package Servlet;

import dao.BillDAO;
import model.Bill;
import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/bill_list")
public class BillListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        EntityManager em = JPAUtil.getEntityManager();  // Lấy EntityManager
        BillDAO dao = new BillDAO(em);                  // Truyền vào DAO

        List<Bill> billList = dao.findAllBills();
        req.setAttribute("billList", billList);

        em.close();  // Đóng EntityManager khi xong
        req.getRequestDispatcher("/WEB-INF/views/bill_list.jsp").forward(req, resp);
    }
}
