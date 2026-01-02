package com.vms.servlet;

import com.vms.dao.AdminDAO;
import com.vms.model.*;
import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private AdminDAO adminDAO;

    @Override
    public void init() {
        // In a real app, inject the connection or use a DataSource
        adminDAO = new AdminDAO();
    }

    /**
     * Helper to verify if the logged-in user is an Admin.
     * Based on your schema: role_id 1 = Admin (assuming 1 is admin)
     */
    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;
        User user = (User) session.getAttribute("currentUser");
        // Assuming role_id 1 is Admin in your 'roles' table
        return user != null && user.getRoleId() == 1;
    }
}
