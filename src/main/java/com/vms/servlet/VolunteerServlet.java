package com.vms.servlet;

import com.vms.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/volunteer/*")
public class VolunteerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isVolunteer(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        // Logic for volunteer dashboard
        req.setAttribute("message", "Welcome Volunteer");
        req.getRequestDispatcher("/WEB-INF/views/volunteer/dashboard.jsp").forward(req, resp);
    }

    private boolean isVolunteer(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            return user != null && user.getRole() == User.Role.VOLUNTEER;
        }
        return false;
    }
}
