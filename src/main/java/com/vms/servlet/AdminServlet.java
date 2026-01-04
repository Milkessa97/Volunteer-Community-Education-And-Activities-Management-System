package com.vms.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.vms.dao.AdminDAO;
import com.vms.model.Announcement;
import com.vms.model.Event;
import com.vms.model.EventWaitlist;
import com.vms.model.Lecture;
import com.vms.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private AdminDAO adminDAO;

    @Override
    public void init() {
     
        adminDAO = new AdminDAO();
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return false;
        User user = (User) session.getAttribute("currentUser");
        
        return user != null && user.getRoleId() == 1;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect("login.jsp?error=unauthorized");
            return;
        }

        String action = request.getParameter("action");
        if (action == null)
            action = "dashboard";

        try {
            switch (action) {
                case "dashboard":
                    int upcoming = adminDAO.countUpcomingLectures();
                    Lecture popular = adminDAO.getMostPopularLecture();
                    int completed = adminDAO.countCompletedSessions();
                    int totalAttendees = adminDAO.countTotalAttendees();
                    double averageAttendees = adminDAO.getAverageAttendeesPerLecture();
                    request.setAttribute("upcomingCount", upcoming);
                    request.setAttribute("popularLecture", popular);
                    request.setAttribute("completed", completed);
                    request.setAttribute("totalAttendees", totalAttendees);
                    request.setAttribute("averageAttendees", averageAttendees);

                    request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
                    break;

                case "lectures":
                    List<Lecture> lectureList = adminDAO.getAllLectures();
                    request.setAttribute("lectures", lectureList);
                    request.getRequestDispatcher("manage-lectures.jsp").forward(request, response);
                    break;
                case "listUsers":
                    List<User> users = adminDAO.getAllUsers();
                    request.setAttribute("users", users);
                    request.getRequestDispatcher("admin-users.jsp").forward(request, response);
                    break;

                case "listEvents":
                    List<Event> events = adminDAO.getAllEvents();
                    request.setAttribute("events", events);
                    request.getRequestDispatcher("admin-events.jsp").forward(request, response);
                    break;

                case "pendingLectures":
                    List<Lecture> pendingLectures = adminDAO.getPendingLectures();
                    request.setAttribute("lectures", pendingLectures);
                    request.getRequestDispatcher("manage-lectures.jsp").forward(request, response);
                    break;

                case "viewWaitlist":
                    long eventId = Long.parseLong(request.getParameter("eventId"));
                    List<EventWaitlist> waitlist = adminDAO.getWaitlist(eventId);
                    request.setAttribute("waitlist", waitlist);
                    request.getRequestDispatcher("admin-waitlist.jsp").forward(request, response);
                    break;

                case "createLecturePage":
                    List<User> volunteers = adminDAO.getVolunteers();
                    request.setAttribute("volunteers", volunteers);
                    request.getRequestDispatcher("create-lecture.jsp").forward(request, response);
                    break;

                case "editLecture":
                    long lectureId = Long.parseLong(request.getParameter("id"));
                    Lecture lectureToEdit = adminDAO.getLectureById(lectureId);
                    List<User> volunteersForEdit = adminDAO.getVolunteers();
                    request.setAttribute("lecture", lectureToEdit);
                    request.setAttribute("volunteers", volunteersForEdit);
                    request.getRequestDispatcher("edit-lecture.jsp").forward(request, response);
                    break;

                default:
                    response.sendRedirect("admin?action=dashboard");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        User admin = (User) request.getSession().getAttribute("currentUser");
        String action = request.getParameter("action");
        String redirectUrl = "admin?action=dashboard";

        try {
            switch (action) {
                case "createEvent":
                    Event event = new Event();
                    populateEventData(request, event);
                    event.setCreatedBy(admin.getUserId());
                    adminDAO.createEvent(event);
                    redirectUrl = "admin?action=listEvents";
                    break;

                case "approveLecture":
                    long lectureId = Long.parseLong(request.getParameter("lectureId"));
                    adminDAO.approveLecture(lectureId, admin.getUserId());
                    redirectUrl = "admin?action=lectures";
                    break;

                case "postAnnouncement":
                    Announcement ann = new Announcement();
                    ann.setTitle(request.getParameter("title"));
                    ann.setContent(request.getParameter("content"));
                    ann.setEventDate(Date.valueOf(request.getParameter("eventDate")));
                    ann.setLocation(request.getParameter("location"));
                    ann.setPostedBy(admin.getUserId());
                    adminDAO.postAnnouncement(ann);
                    redirectUrl = "admin?action=listAnnouncements";
                    break;

                case "moveWaitlist":
                    long wlId = Long.parseLong(request.getParameter("waitlistId"));
                    long userId = Long.parseLong(request.getParameter("userId"));
                    long evId = Long.parseLong(request.getParameter("eventId"));
                    adminDAO.moveFromWaitlistToRegistration(wlId, evId, userId);
                    redirectUrl = "admin?action=viewWaitlist&eventId=" + evId;
                    break;

                case "createLecture":
                    Lecture newLecture = new Lecture();
                    newLecture.setTitle(request.getParameter("title"));
                    newLecture.setDescription(request.getParameter("description"));
                    newLecture.setVolunteerId(Long.parseLong(request.getParameter("volunteerId")));
                    newLecture.setLectureDate(Date.valueOf(request.getParameter("date")));
                    newLecture.setStartTime(Time.valueOf(request.getParameter("time") + ":00"));
                    newLecture.setLocation(request.getParameter("location"));
                    adminDAO.createLecture(newLecture);
                    redirectUrl = "admin?action=lectures";
                    break;

                case "deleteLecture":
                    long lectureIdToDelete = Long.parseLong(request.getParameter("id"));
                    adminDAO.deleteLecture(lectureIdToDelete);
                    redirectUrl = "admin?action=lectures";
                    break;
            }
            response.sendRedirect(redirectUrl);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("errorMessage", "Database Error: " + ex.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void populateEventData(HttpServletRequest request, Event e) {
        e.setTitle(request.getParameter("title"));
        e.setDescription(request.getParameter("description"));
        e.setEventDate(Date.valueOf(request.getParameter("eventDate")));
        e.setStartTime(Time.valueOf(request.getParameter("startTime") + ":00"));
        e.setEndTime(Time.valueOf(request.getParameter("endTime") + ":00"));
        e.setLocation(request.getParameter("location"));
        e.setCapacity(Integer.parseInt(request.getParameter("capacity")));
        e.setStatus(request.getParameter("status"));
    }
}