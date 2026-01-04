package com.vms.servlet;

import com.vms.dao.UserDAO;
import com.vms.model.User;
import com.vms.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userParam = request.getParameter("username");
        String passParam = request.getParameter("password");

        try (Connection conn = DBUtil.getConnection()) {

            UserDAO dao = new UserDAO();
            User user = dao.findByUsername(userParam);

            if (user != null && user.getPassword().equals(passParam)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("currentUser", user);

                User.Role role = user.getRole();

                switch (role) {
                    case ADMIN:
                        session.setAttribute("admin", user);
                        response.sendRedirect("admin?action=dashboard");
                        break;
                    case VOLUNTEER:
                        session.setAttribute("volunteer", user);
                        response.sendRedirect("volunteer?action=dashboard");
                        break;
                    case STUDENT:
                        session.setAttribute("student", user);
                        response.sendRedirect("student?action=dashboard");
                        break;
                    default:
                        response.sendRedirect("login.jsp?error=invalid_role");
                        break;
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