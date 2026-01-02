package com.vms.servlet;

import com.vms.dao.UserDAO;
import com.vms.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userParam = request.getParameter("username");
        String passParam = request.getParameter("password");

        UserDAO dao = new UserDAO();
        try {
            User user = dao.findByUsername(userParam);

            // Plain text comparison logic
            if (user != null && user.getPassword().equals(passParam)) {
                // Store user in session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // Redirect based on role
                switch (user.getRole().toUpperCase()) {
                    case "ADMIN":
                        response.sendRedirect("admin/dashboard.jsp");
                        break;
                    case "VOLUNTEER":
                        response.sendRedirect("volunteer/dashboard.jsp");
                        break;
                    case "STUDENT":
                        response.sendRedirect("student/dashboard.jsp");
                        break;
                    default:
                        response.sendRedirect("login.jsp?error=invalid_role");
                }
            } else {
                response.sendRedirect("login.jsp?error=invalid_credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=server_error");
        }
    }
}