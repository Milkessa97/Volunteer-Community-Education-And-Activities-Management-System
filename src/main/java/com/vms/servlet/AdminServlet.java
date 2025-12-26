package com.vms.servlet;

import com.vms.dao.AdminDAO;
import com.vms.model.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class AdminServlet extends HttpServlet {

    private AdminDAO adminDAO;

    @Override
    public void init() {
        adminDAO = new AdminDAO();
    }

    private boolean checkAdmin(HttpSession session) {
        User admin = (session != null) ? (User) session.getAttribute("admin") : null;
        return admin != null && admin.getRole() == User.Role.ADMIN;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (!checkAdmin(session)) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        User admin = (User) session.getAttribute("admin");
        String action = request.getParameter("action");
        if (action == null)
            action = "dashboard";

        switch (action) {
            case "dashboard":
                request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
                break;

            case "listUsers":
                List<User> users = adminDAO.getAllAdmins(); // temporary for admins only
                request.setAttribute("users", users);
                request.getRequestDispatcher("admin-users.jsp").forward(request, response);
                break;

            case "listEvents":
                List<Event> events = adminDAO.getAllEvents();
                request.setAttribute("events", events);
                request.getRequestDispatcher("admin-events.jsp").forward(request, response);
                break;

            case "listLectures":
            case "pendingLectures":
                List<Lecture> lectures = adminDAO.getPendingLectures();
                request.setAttribute("lectures", lectures);
                request.getRequestDispatcher("admin-lectures.jsp").forward(request, response);
                break;

            case "listAnnouncements":
                List<Announcement> announcements = adminDAO.getAllAnnouncements();
                request.setAttribute("announcements", announcements);
                request.getRequestDispatcher("admin-announcements.jsp").forward(request, response);
                break;

            case "viewWaitlist":
                try {
                    int eventId = Integer.parseInt(request.getParameter("eventId"));
                    List<EventWaitlist> waitlist = adminDAO.getWaitlist(eventId);
                    request.setAttribute("waitlist", waitlist);
                    request.getRequestDispatcher("admin-waitlist.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    response.sendRedirect("admin?action=listEvents");
                }
                break;

            default:
                response.sendRedirect("admin?action=dashboard");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (!checkAdmin(session)) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        User admin = (User) session.getAttribute("admin");
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "createEvent":
                    Event e = new Event();
                    e.setTitle(request.getParameter("title"));
                    e.setDescription(request.getParameter("description"));
                    e.setEventDate(Date.valueOf(request.getParameter("eventDate")));
                    e.setStartTime(Time.valueOf(request.getParameter("startTime")));
                    e.setEndTime(Time.valueOf(request.getParameter("endTime")));
                    e.setLocation(request.getParameter("location"));
                    e.setCapacity(Integer.parseInt(request.getParameter("capacity")));
                    e.setStatus(request.getParameter("status"));
                    e.setCreatedBy(admin.getId());
                    adminDAO.createEvent(e);
                    break;

                case "updateEvent":
                    Event ue = new Event();
                    ue.setEventId(Integer.parseInt(request.getParameter("eventId")));
                    ue.setTitle(request.getParameter("title"));
                    ue.setDescription(request.getParameter("description"));
                    ue.setEventDate(Date.valueOf(request.getParameter("eventDate")));
                    ue.setStartTime(Time.valueOf(request.getParameter("startTime")));
                    ue.setEndTime(Time.valueOf(request.getParameter("endTime")));
                    ue.setLocation(request.getParameter("location"));
                    ue.setCapacity(Integer.parseInt(request.getParameter("capacity")));
                    ue.setStatus(request.getParameter("status"));
                    adminDAO.updateEvent(ue);
                    break;

                case "deleteEvent":
                    int eventId = Integer.parseInt(request.getParameter("eventId"));
                    adminDAO.deleteEvent(eventId);
                    break;

                case "approveLecture":
                    int lectureId = Integer.parseInt(request.getParameter("lectureId"));
                    adminDAO.approveLecture(lectureId, admin.getId());
                    break;

                case "postAnnouncement":
                    Announcement ann = new Announcement();
                    ann.setTitle(request.getParameter("title"));
                    ann.setContent(request.getParameter("content"));
                    ann.setEventDate(Date.valueOf(request.getParameter("eventDate")));
                    ann.setLocation(request.getParameter("location"));
                    ann.setPostedBy(admin.getId());
                    adminDAO.postAnnouncement(ann);
                    break;

                case "moveWaitlist":
                    int waitlistId = Integer.parseInt(request.getParameter("waitlistId"));
                    int userId = Integer.parseInt(request.getParameter("userId"));
                    int evId = Integer.parseInt(request.getParameter("eventId"));
                    adminDAO.moveFromWaitlistToRegistration(waitlistId, evId, userId);
                    break;

                case "removeWaitlist":
                    int wlId = Integer.parseInt(request.getParameter("waitlistId"));
                    adminDAO.removeFromWaitlist(wlId);
                    break;

                default:
                    break;
            }

            // Redirect based on action
            switch (action) {
                case "createEvent":
                case "updateEvent":
                case "deleteEvent":
                    response.sendRedirect("admin?action=listEvents");
                    break;

                case "approveLecture":
                    response.sendRedirect("admin?action=pendingLectures");
                    break;

                case "postAnnouncement":
                    response.sendRedirect("admin?action=listAnnouncements");
                    break;

                case "moveWaitlist":
                    int evId = Integer.parseInt(request.getParameter("eventId"));
                    response.sendRedirect("admin?action=viewWaitlist&eventId=" + evId);
                    break;

                case "removeWaitlist":
                    response.sendRedirect("admin?action=listEvents");
                    break;

                default:
                    response.sendRedirect("admin?action=dashboard");
                    break;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
