package com.vms.filter;

import com.vms.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String path = request.getServletPath();

        // 1. Allow access to login page, login servlet, and static assets
        if (path.equals("/login") || path.endsWith(".css") || path.endsWith(".js") || path.equals("/login.jsp")) {
            chain.doFilter(req, res);
            return;
        }

        // 2. Check Authentication: Is the user logged in?
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=please_login");
            return;
        }

        // 3. Check Authorization: Role-Based access
        String role = user.getRole().toUpperCase();

        if (path.startsWith("/admin") && !role.equals("ADMIN")) {
            response.sendError(403, "Access Denied: Admins Only");
            return;
        }

        if (path.startsWith("/volunteer") && !role.equals("VOLUNTEER")) {
            response.sendError(403, "Access Denied: Volunteers Only");
            return;
        }

        if (path.startsWith("/student") && !role.equals("STUDENT")) {
            response.sendError(403, "Access Denied: Students Only");
            return;
        }

        // If all checks pass, proceed
        chain.doFilter(req, res);
    }
}