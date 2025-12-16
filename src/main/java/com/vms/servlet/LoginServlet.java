package com.vms.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;

import java.io.IOException;

import com.vms.dao.UserDAO;
import com.vms.model.User;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            UserDAO dao = new UserDAO();
            User user = dao.findByUsername(username);

            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                HttpSession session = req.getSession(true);
                session.setAttribute("user", user);

                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Login successful");
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write("Invalid credentials");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Server error");
        }
    }
}
