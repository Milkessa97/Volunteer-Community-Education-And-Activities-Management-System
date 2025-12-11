package com.vms.servlet;

import com.vms.dao.UserDAO;
import com.vms.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("login".equals(action)) {
            handleLogin(req, resp);
        } else if ("register".equals(action)) {
            handleRegister(req, resp);
        } else if ("logout".equals(action)) {
            handleLogout(req, resp);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        // In a real app, hash the password!
        String password = req.getParameter("password");

        User user = userDAO.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            switch (user.getRole()) {
                case ADMIN:
                    resp.sendRedirect("admin/dashboard.jsp");
                    break;
                case VOLUNTEER:
                    resp.sendRedirect("volunteer/dashboard.jsp");
                    break;
                case STUDENT:
                    resp.sendRedirect("student/dashboard.jsp");
                    break;
            }
        } else {
            req.setAttribute("error", "Invalid credentials");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Basic registration logic
        User user = new User();
        user.setUsername(req.getParameter("username"));
        user.setPassword(req.getParameter("password")); // Hash this!
        user.setEmail(req.getParameter("email"));
        user.setRole(User.Role.valueOf(req.getParameter("role"))); // Validate this!

        if (userDAO.createUser(user)) {
            resp.sendRedirect("login.jsp?msg=Registered");
        } else {
            req.setAttribute("error", "Registration failed");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect("login.jsp");
    }
}
