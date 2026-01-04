package com.vms.dao;

import com.vms.model.*;
import com.vms.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    public void moveFromWaitlistToRegistration(long waitlistId, long eventId, long userId) throws SQLException {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            // Start transaction
            conn.setAutoCommit(false);

            // 1. Insert into registrations
            String insertSql = "INSERT INTO event_registrations (event_id, user_id, registration_status) VALUES (?, ?, 'CONFIRMED')";
            try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                psInsert.setLong(1, eventId);
                psInsert.setLong(2, userId);
                psInsert.executeUpdate();
            }

            // 2. Delete from waitlist
            String deleteSql = "DELETE FROM event_waitlist WHERE waitlist_id = ?";
            try (PreparedStatement psDelete = conn.prepareStatement(deleteSql)) {
                psDelete.setLong(1, waitlistId);
                psDelete.executeUpdate();
            }

            // Commit both changes
            conn.commit();
        } catch (SQLException e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
    // --- EVENT MANAGEMENT ---

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        // Join with users to get the name of the creator
        String sql = "SELECT e.*, u.full_name FROM events e " +
                "JOIN users u ON e.created_by = u.user_id " +
                "ORDER BY e.event_date DESC";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Event e = new Event();
                e.setEventId(rs.getInt("event_id"));
                e.setTitle(rs.getString("title"));
                e.setDescription(rs.getString("description"));
                e.setEventDate(rs.getDate("event_date"));
                e.setStartTime(rs.getTime("start_time"));
                e.setEndTime(rs.getTime("end_time"));
                e.setLocation(rs.getString("location"));
                e.setCapacity(rs.getInt("capacity"));
                e.setStatus(rs.getString("status"));
                e.setCreatedBy(rs.getInt("created_by"));
                // If you added a 'creatorName' field to your Event model:
                // e.setCreatorName(rs.getString("full_name"));
                events.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<EventWaitlist> getWaitlist(long eventId) {
        List<EventWaitlist> list = new ArrayList<>();
        String sql = "SELECT w.*, u.full_name, u.username FROM event_waitlist w " +
                "JOIN users u ON w.user_id = u.user_id " +
                "WHERE w.event_id = ? ORDER BY w.position ASC";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EventWaitlist ew = new EventWaitlist();
                    ew.setWaitlistId(rs.getInt("waitlist_id"));
                    ew.setEventId(rs.getInt("event_id"));
                    ew.setUserId(rs.getInt("user_id"));
                    ew.setPosition(rs.getInt("position"));
                    // You can add a 'userName' field to your EventWaitlist model to show names
                    list.add(ew);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    // --- DASHBOARD METHODS ---

    public int countUpcomingLectures() {
        String sql = "SELECT COUNT(*) FROM lectures WHERE status = 'pending'";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // --- NEW DASHBOARD METRICS ---

    /**
     * Counts lectures that occurred in the past.
     */
    public int countCompletedSessions() {
        String sql = "SELECT COUNT(*) FROM lectures WHERE status = 'approved'";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Counts total volunteer registrations across all lectures.
     */
    public int countTotalAttendees() {
        String sql = "SELECT COUNT(*) FROM lecture_registrations";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Calculates the average number of volunteers per lecture.
     */
    public double getAverageAttendeesPerLecture() {
        String sql = "SELECT AVG(attendee_count) FROM (" +
                "  SELECT COUNT(registration_id) as attendee_count " +
                "  FROM lecture_registrations " +
                "  GROUP BY lecture_id" +
                ") as counts";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public Lecture getMostPopularLecture() {
        // Joins lectures with registrations to find the one with the most students
        String sql = "SELECT l.title, u.full_name as instructor_name " +
                "FROM lectures l " +
                "JOIN users u ON l.volunteer_id = u.user_id " +
                "LEFT JOIN lecture_registrations r ON l.lecture_id = r.lecture_id " +
                "GROUP BY l.lecture_id " +
                "ORDER BY COUNT(r.registration_id) DESC LIMIT 1";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Lecture l = new Lecture();
                l.setTitle(rs.getString("title"));
                l.setInstructor(rs.getString("instructor_name"));
                return l;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // --- USER MANAGEMENT ---

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.*, r.role_name FROM users u JOIN roles r ON u.role_id = r.role_id";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setFullName(rs.getString("full_name"));
                u.setUsername(rs.getString("username"));
                u.setRoleId(rs.getLong("role_id"));
                // If your User model has a roleName field:
                // u.setRoleName(rs.getString("role_name"));
                users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // --- LECTURE MANAGEMENT ---

    public List<Lecture> getAllLectures() {
        List<Lecture> lectures = new ArrayList<>();

        // Using LEFT JOIN so lectures without an assigned volunteer still appear
        String sql = "SELECT l.lecture_id, l.title, l.description, l.lecture_date, " +
                "l.start_time, l.end_time, l.location, l.status, " +
                "u.full_name as instructor_name " +
                "FROM lectures l " +
                "LEFT JOIN users u ON l.volunteer_id = u.user_id " +
                "ORDER BY l.lecture_date DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Lecture l = new Lecture();

                // Primary Data
                l.setLectureId(rs.getLong("lecture_id"));
                l.setTitle(rs.getString("title"));
                l.setDescription(rs.getString("description"));
                l.setLectureDate(rs.getDate("lecture_date"));

                // Schedule Data (Make sure these setters exist in your Lecture.java)
                l.setStartTime(rs.getTime("start_time"));
                l.setEndTime(rs.getTime("end_time"));

                // Metadata
                l.setLocation(rs.getString("location"));
                l.setStatus(rs.getString("status"));

                // Instructor Data from the JOIN
                // If instructor_name is NULL (due to LEFT JOIN), it safely sets to null
                l.setInstructor(rs.getString("instructor_name"));

                lectures.add(l);
            }

            // Debugging line - check your console/logs to see if this is 0
            System.out.println("DEBUG: Retrieved " + lectures.size() + " lectures from database.");

        } catch (SQLException e) {
            System.err.println("Error in getAllLectures: " + e.getMessage());
            e.printStackTrace();
        }
        return lectures;
    }

    /**
     * Deletes a lecture from the database.
     * Due to our schema constraints, this will work if no students
     * are registered yet. If students are registered, you might need to
     * delete registrations first or use ON DELETE CASCADE.
     */
    public boolean deleteLecture(long lectureId) throws SQLException {
        String sql = "DELETE FROM lectures WHERE lecture_id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, lectureId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Fetches a single lecture by ID (needed for the Edit page).
     */
    public Lecture getLectureById(long id) {
        String sql = "SELECT l.*, u.full_name as instructor_name " +
                "FROM lectures l " +
                "JOIN users u ON l.volunteer_id = u.user_id " +
                "WHERE l.lecture_id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Lecture l = new Lecture();
                    l.setLectureId(rs.getLong("lecture_id"));
                    l.setTitle(rs.getString("title"));
                    l.setDescription(rs.getString("description"));
                    l.setLectureDate(rs.getDate("lecture_date"));
                    l.setLocation(rs.getString("location"));
                    l.setStatus(rs.getString("status"));
                    l.setInstructor(rs.getString("instructor_name"));
                    return l;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Lecture> getPendingLectures() {
        List<Lecture> lectures = new ArrayList<>();
        String sql = "SELECT l.*, u.full_name FROM lectures l JOIN users u ON l.volunteer_id = u.user_id WHERE l.status = 'PENDING'";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Lecture l = new Lecture();
                l.setLectureId(rs.getLong("lecture_id"));
                l.setTitle(rs.getString("title"));
                l.setLectureDate(rs.getDate("lecture_date"));
                l.setInstructor(rs.getString("full_name"));
                lectures.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lectures;
    }

    public void approveLecture(long lectureId, long adminId) throws SQLException {
        String sql = "UPDATE lectures SET status = 'APPROVED', approved_by = ?, approved_at = NOW() WHERE lecture_id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, adminId);
            ps.setLong(2, lectureId);
            ps.executeUpdate();
        }
    }

    // --- EVENT MANAGEMENT ---

    public void createEvent(Event event) throws SQLException {
        String sql = "INSERT INTO events (title, description, event_date, start_time, end_time, location, capacity, status, created_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setDate(3, event.getEventDate());
            ps.setTime(4, event.getStartTime());
            ps.setTime(5, event.getEndTime());
            ps.setString(6, event.getLocation());
            ps.setInt(7, event.getCapacity());
            ps.setString(8, event.getStatus());
            ps.setLong(9, event.getCreatedBy());
            ps.executeUpdate();
        }
    }

    /**
     * Creates a new lecture in the database.
     * Sets initial status to 'PENDING' for admin approval.
     */
    public void createLecture(Lecture lecture) throws SQLException {
        String sql = "INSERT INTO lectures (title, description, lecture_date, start_time, location, volunteer_id, status) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, 'PENDING')";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lecture.getTitle());
            ps.setString(2, lecture.getDescription());
            ps.setDate(3, lecture.getLectureDate());
            ps.setTime(4, lecture.getStartTime());
            ps.setString(5, lecture.getLocation());
            ps.setLong(6, lecture.getVolunteerId());
            ps.executeUpdate();
        }
    }

    /**
     * Fetches all users with the Volunteer role (role_id = 2).
     * Used for populating the volunteer dropdown in create lecture form.
     */
    public List<User> getVolunteers() {
        List<User> volunteers = new ArrayList<>();
        String sql = "SELECT user_id, full_name, username FROM users WHERE role_id = ? ORDER BY full_name";

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Explicitly set as a Long to match BIGINT
            ps.setLong(1, 2L); 
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setFullName(rs.getString("full_name"));
                    u.setUsername(rs.getString("username"));
                    u.setRoleId(2L);
                    volunteers.add(u);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return volunteers;
    }

    // --- ANNOUNCEMENTS ---

    public void postAnnouncement(Announcement ann) throws SQLException {
        String sql = "INSERT INTO announcements (title, content, event_date, location, posted_by) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ann.getTitle());
            ps.setString(2, ann.getContent());
            ps.setDate(3, ann.getEventDate());
            ps.setString(4, ann.getLocation());
            ps.setLong(5, ann.getPostedBy());
            ps.executeUpdate();
        }
    }
}