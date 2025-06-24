package Servlet;

import dao.BillDAO;
import dao.BillDetailDAO;
import model.Bill;
import model.BillDetail;
import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/bill_detail")
public class BillDetailServlet extends HttpServlet {
    private BillDAO billDAO;
    private BillDetailDAO billDetailDAO;

    @Override
    public void init() throws ServletException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        billDAO = new BillDAO(em);
        billDetailDAO = new BillDetailDAO(em);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int billId = Integer.parseInt(request.getParameter("id"));
            Bill bill = billDAO.findById(billId);

            if (bill == null) {
                request.setAttribute("errorMessage", "Không tìm thấy hóa đơn.");
                request.setAttribute("billDetails", null);
                request.setAttribute("bill", null);
            } else {
                List<BillDetail> details = billDetailDAO.findByBillId(billId);
                request.setAttribute("bill", billDAO.findById(billId)); // nếu cần thông tin bill
                request.setAttribute("details", details);
            }

            request.getRequestDispatcher("/WEB-INF/views/bill_detail.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("bill_list");
        }
    }
}
