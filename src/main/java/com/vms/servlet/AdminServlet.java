package com.vms.servlet;

import com.vms.dao.AdminDAO;
import com.vms.model.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

public class AdminServlet extends HttpServlet {

    private AdminDAO adminDAO = new AdminDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute("admin");

        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        switch (action) {
            case "listUsers":
                List<User> users = adminDAO.getAllAdmins(); // you can modify to get all users
                request.setAttribute("users", users);
                request.getRequestDispatcher("admin-users.jsp").forward(request, response);
                break;

            case "listEvents":
                List<Event> events = adminDAO.getAllEvents();
                request.setAttribute("events", events);
                request.getRequestDispatcher("admin-events.jsp").forward(request, response);
                break;

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
                int eventId = Integer.parseInt(request.getParameter("eventId"));
                List<EventWaitlist> waitlist = adminDAO.getWaitlist(eventId);
                request.setAttribute("waitlist", waitlist);
                request.getRequestDispatcher("admin-waitlist.jsp").forward(request, response);
                break;

            default:
                response.sendRedirect("admin-dashboard.jsp");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute("admin");

        if (admin == null || admin.getRole() != User.Role.ADMIN) {
            response.sendRedirect("admin-login.jsp");
            return;
        }

        switch (action) {
            case "login":
                // handle login separately or in LoginServlet
                break;

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

        response.sendRedirect("admin?action=listEvents"); // default redirect
    }
}
