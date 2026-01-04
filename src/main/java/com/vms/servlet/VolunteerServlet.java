package com.vms.servlet;

import com.vms.dao.VolunteerDAO;
import com.vms.model.*;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/volunteer")
public class VolunteerServlet extends HttpServlet {

    private VolunteerDAO volunteerDAO;

    @Override
    public void init() {
        volunteerDAO = new VolunteerDAO();
    }

    /**
     * Security check: Ensures user is logged in and has the 'Volunteer' role.
     * Assuming role_id 2 = Volunteer.
     */
    private User getAuthenticatedVolunteer(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return null;
        User user = (User) session.getAttribute("currentUser");
        return (user != null && user.getRoleId() == 2) ? user : null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User volunteer = getAuthenticatedVolunteer(request);
        if (volunteer == null) {
            response.sendRedirect("login.jsp?error=unauthorized");
            return;
        }

        String action = request.getParameter("action");
        if (action == null)
            action = "dashboard";

        try {
            switch (action) {
                case "dashboard":
                    // Statistics for the 3 cards in your HTML
                    int assignedCount = volunteerDAO.countAssignedLectures(volunteer.getUserId());
                    int studentCount = volunteerDAO.countTotalStudentsReached(volunteer.getUserId());
                    int upcomingCount = volunteerDAO.countUpcomingSessions(volunteer.getUserId());

                    request.setAttribute("assignedCount", assignedCount);
                    request.setAttribute("studentCount", studentCount);
                    request.setAttribute("upcomingCount", upcomingCount);

                    // To greet the user by name "Sarah Johnson"
                    request.setAttribute("volunteerName", volunteer.getFullName().trim().split("\\s+")[0]);

                    request.getRequestDispatcher("volunteer-dashboard.jsp").forward(request, response);
                    break;

                case "myLectures":
                    List<Lecture> myLectures = volunteerDAO.getLecturesByVolunteer(volunteer.getUserId());
                    request.setAttribute("assignedLectures", myLectures);
                    request.getRequestDispatcher("volunteer-myLectures.jsp").forward(request, response);
                    break;

                default:
                    response.sendRedirect("volunteer?action=dashboard");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}