package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebFilter("/*")
public class AuthAndRoleFilter implements Filter {

    private final Map<String, List<String>> accessMap = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        // Cấu hình các URL (servlet mapping hoặc đường dẫn) và vai trò được phép truy cập
        /*accessMap.put("home.jsp", Arrays.asList("admin", "thu_ngan", "phuc_vu"));*/
        accessMap.put("home", Arrays.asList("admin", "thu_ngan", "phuc_vu"));
        accessMap.put("order_list", Arrays.asList("admin", "thu_ngan", "phuc_vu"));
        accessMap.put("bill_list", Arrays.asList("admin", "thu_ngan"));
        accessMap.put("table_list", Arrays.asList("admin", "thu_ngan", "phuc_vu"));
        accessMap.put("menu", Arrays.asList("admin", "thu_ngan", "phuc_vu"));
        accessMap.put("user_list", Collections.singletonList("admin"));
        accessMap.put("product_list", Collections.singletonList("admin"));
        accessMap.put("statistics", Arrays.asList("admin", "thu_ngan"));
    }
    /*@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        String uri = req.getRequestURI();  // Ví dụ: /project/order_list
        String contextPath = req.getContextPath(); // Ví dụ: /project

        String page = uri.substring(contextPath.length() + 1); // bỏ phần /project/ => còn order_list
        if (page.contains("/")) {
            page = page.substring(page.lastIndexOf("/") + 1); // chỉ lấy phần cuối, ví dụ: "order_list"
        }

        // In ra kiểm tra
        System.out.println("Đang truy cập: " + page);

        // Bỏ qua kiểm tra cho các trang public
        if (page.equals("login.jsp") || page.equals("login") || page.equals("unauthorized.jsp")) {
            chain.doFilter(request, response);
            return;
        }

        // Kiểm tra đăng nhập
        if (session == null || session.getAttribute("userRole") == null) {
            res.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        String userRole = (String) session.getAttribute("userRole");

        // Kiểm tra phân quyền
        List<String> allowedRoles = accessMap.get(page);
        if (allowedRoles != null && !allowedRoles.contains(userRole)) {
            res.sendRedirect(contextPath + "/unauthorized.jsp");
            return;
        }

        chain.doFilter(request, response);
    }*/

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        String uri = req.getRequestURI();  // Ví dụ: /project/home
        String contextPath = req.getContextPath(); // Ví dụ: /project

        String page = uri.substring(contextPath.length() + 1); // còn "home"

        if (page.contains("/")) {
            page = page.substring(page.lastIndexOf("/") + 1);
        }

        // Nếu URL có đuôi .jsp thì bỏ đuôi đi (để chuẩn hóa)
        if (page.endsWith(".jsp")) {
            page = page.substring(0, page.length() - 4); // ví dụ: "home.jsp" -> "home"
        }

        // Bỏ qua các trang public
        if (page.equals("login") || page.equals("unauthorized")) {
            chain.doFilter(request, response);
            return;
        }

        if (session == null || session.getAttribute("userRole") == null) {
            res.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        String userRole = (String) session.getAttribute("userRole");

        // Kiểm tra phân quyền theo tên page đã chuẩn hóa
        List<String> allowedRoles = accessMap.get(page);
        if (allowedRoles != null && !allowedRoles.contains(userRole)) {
            res.sendRedirect(contextPath + "/unauthorized.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Không cần xử lý gì khi filter bị hủy
    }
}
