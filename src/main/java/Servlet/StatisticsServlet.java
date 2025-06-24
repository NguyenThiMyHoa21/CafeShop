package Servlet;

import dao.StatisticsDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet {

    private StatisticsDAO statisticsDAO;

    @Override
    public void init() {
        statisticsDAO = new StatisticsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BigDecimal todayRevenue = statisticsDAO.getTodayRevenue();
        long todayOrderCount = statisticsDAO.getTodayOrderCount();

        request.setAttribute("todayRevenue", todayRevenue);
        request.setAttribute("todayOrderCount", todayOrderCount);

        request.getRequestDispatcher("/statistics.jsp").forward(request, response);
    }
}
