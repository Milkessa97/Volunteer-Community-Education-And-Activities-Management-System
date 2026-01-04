package com.vms.servlet;

import com.vms.dao.StudentDAO;
import com.vms.model.Lecture;
import com.vms.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/student")
public class StudentServlet extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    private boolean isStudent(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return false;
        User user = (User) session.getAttribute("currentUser");
        return user != null && user.getRoleId() == 3;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isStudent(request)) {
            response.sendRedirect("login.jsp?error=unauthorized");
            return;
        }

        User student = (User) request.getSession().getAttribute("currentUser");
        String action = request.getParameter("action");
        if (action == null)
            action = "dashboard";

        try {
            switch (action) {
                case "dashboard":
                    List<Lecture> available = studentDAO.getAvailableLectures();
                    List<Lecture> registered = studentDAO.getStudentRegistrations(student.getUserId());
                    int upcoming = studentDAO.countUpcomingForStudent(student.getUserId());
                    int completed = studentDAO.countCompletedForStudent(student.getUserId());
                    if (available == null)
                        available = new ArrayList<>();
                    int regCount = (registered != null) ? registered.size() : 0;

                    request.setAttribute("availableLectures", available);
                    request.setAttribute("myLecturesCount", regCount);
                    request.setAttribute("upcomingSessions", upcoming);
                    request.setAttribute("completedSessions", completed);
                    request.getRequestDispatcher("student-dashboard.jsp").forward(request, response);
                    break;

                case "mySchedule":
                    List<Lecture> myLectures = studentDAO.getStudentRegistrations(student.getUserId());
                    if (myLectures == null)
                        myLectures = new ArrayList<>();


                    List<Lecture> upcomingLectures = new ArrayList<>();
                    List<Lecture> completedLectures = new ArrayList<>();
                    java.sql.Date today = new java.sql.Date(System.currentTimeMillis());

                    for (Lecture lecture : myLectures) {
                        if (lecture.getLectureDate() != null && lecture.getLectureDate().before(today)) {
                            completedLectures.add(lecture);
                        } else {
                            upcomingLectures.add(lecture);
                        }
                    }

                    request.setAttribute("upcomingLectures", upcomingLectures);
                    request.setAttribute("completedLectures", completedLectures);
                    request.getRequestDispatcher("student-mySchedule.jsp").forward(request, response);
                    break;

                case "lectures":

                    List<Lecture> allLectures = studentDAO.getAvailableLectures();
                    if (allLectures == null)
                        allLectures = new ArrayList<>();


                    java.util.Map<Long, Boolean> registrationStatus = new java.util.HashMap<>();
                    for (Lecture lecture : allLectures) {
                        boolean isRegistered = studentDAO.isStudentRegistered(
                                student.getUserId(),
                                (int) lecture.getLectureId());
                        registrationStatus.put(lecture.getLectureId(), isRegistered);
                    }

                    request.setAttribute("availableLectures", allLectures);
                    request.setAttribute("registrationStatus", registrationStatus);
                    request.getRequestDispatcher("browse-lectures.jsp").forward(request, response);
                    break;

                default:
                    response.sendRedirect("student?action=dashboard");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isStudent(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        User student = (User) request.getSession().getAttribute("currentUser");
        String action = request.getParameter("action");

        try {
            if ("register".equals(action)) {
                String lectureIdStr = request.getParameter("lectureId");
                if (lectureIdStr != null) {
                    int lectureId = Integer.parseInt(lectureIdStr);
                    boolean success = studentDAO.registerForLecture(student.getUserId(), lectureId);

                    if (success) {
                        // Success feedback
                        request.getSession().setAttribute("message", "Successfully joined!");
                    } else {
                        // Fail feedback
                        request.getSession().setAttribute("error", "Failed to join. Session full or already joined.");
                    }
                }

                response.sendRedirect("student?action=lectures");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("student?action=dashboard&error=true");
        }
    }
}