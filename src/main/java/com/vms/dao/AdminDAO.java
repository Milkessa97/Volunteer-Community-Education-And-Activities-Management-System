package com.vms.dao;

import com.vms.model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    // --- DB connection ---
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/your_db_name", "username", "password");
    }

    // --- 1️⃣ Admin management ---
    public boolean createAdmin(User admin) {
        admin.setRole(User.Role.ADMIN); // ensure role is ADMIN
        String sql = "INSERT INTO users (full_name, email, password, role_id) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getEmail());
            ps.setString(3, admin.getPassword());
            ps.setInt(4, 1); // assuming role_id=1 is ADMIN in roles table
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getAllAdmins() {
        List<User> admins = new ArrayList<>();
        String sql = "SELECT u.user_id, u.full_name, u.email, r.role_name " +
                "FROM users u JOIN roles r ON u.role_id = r.role_id " +
                "WHERE r.role_name='ADMIN'";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setRole(User.Role.ADMIN);
                admins.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    public boolean changeUserRole(int userId, int roleId) {
        String sql = "UPDATE users SET role_id=? WHERE user_id=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roleId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // --- 2️⃣ Event CRUD ---
    public boolean createEvent(Event event) {
        String sql = "INSERT INTO events (title, description, event_date, start_time, end_time, location, capacity, status, created_by) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setDate(3, event.getEventDate());
            ps.setTime(4, event.getStartTime());
            ps.setTime(5, event.getEndTime());
            ps.setString(6, event.getLocation());
            ps.setInt(7, event.getCapacity());
            ps.setString(8, event.getStatus());
            ps.setInt(9, event.getCreatedBy());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("event_id"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                event.setEventDate(rs.getDate("event_date"));
                event.setStartTime(rs.getTime("start_time"));
                event.setEndTime(rs.getTime("end_time"));
                event.setLocation(rs.getString("location"));
                event.setCapacity(rs.getInt("capacity"));
                event.setStatus(rs.getString("status"));
                event.setCreatedBy(rs.getInt("created_by"));
                event.setCreatedAt(rs.getTimestamp("created_at"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public boolean updateEvent(Event event) {
        String sql = "UPDATE events SET title=?, description=?, event_date=?, start_time=?, end_time=?, location=?, capacity=?, status=? "
                +
                "WHERE event_id=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, event.getTitle());
            ps.setString(2, event.getDescription());
            ps.setDate(3, event.getEventDate());
            ps.setTime(4, event.getStartTime());
            ps.setTime(5, event.getEndTime());
            ps.setString(6, event.getLocation());
            ps.setInt(7, event.getCapacity());
            ps.setString(8, event.getStatus());
            ps.setInt(9, event.getEventId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM events WHERE event_id=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // --- 3️⃣ Lecture approval ---
    public List<Lecture> getPendingLectures() {
        List<Lecture> lectures = new ArrayList<>();
        String sql = "SELECT * FROM lectures WHERE status!='Approved'";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Lecture lecture = new Lecture();
                lecture.setLectureId(rs.getInt("lecture_id"));
                lecture.setTitle(rs.getString("title"));
                lecture.setDescription(rs.getString("description"));
                lecture.setLectureDate(rs.getDate("lecture_date"));
                lecture.setStartTime(rs.getTime("start_time"));
                lecture.setEndTime(rs.getTime("end_time"));
                lecture.setLocation(rs.getString("location"));
                lecture.setStatus(rs.getString("status"));
                lecture.setVolunteerId(rs.getInt("volunteer_id"));
                lecture.setCreatedBy(rs.getInt("created_by"));
                lectures.add(lecture);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lectures;
    }

    public boolean approveLecture(int lectureId, int adminId) {
        String sql = "UPDATE lectures SET status='Approved', approved_by=?, approved_at=CURRENT_TIMESTAMP WHERE lecture_id=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, adminId);
            ps.setInt(2, lectureId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // --- 4️⃣ Announcements ---
    public boolean postAnnouncement(Announcement ann) {
        String sql = "INSERT INTO announcements (title, content, event_date, location, posted_by) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ann.getTitle());
            ps.setString(2, ann.getContent());
            ps.setDate(3, ann.getEventDate());
            ps.setString(4, ann.getLocation());
            ps.setInt(5, ann.getPostedBy());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Announcement> getAllAnnouncements() {
        List<Announcement> list = new ArrayList<>();
        String sql = "SELECT * FROM announcements";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Announcement a = new Announcement();
                a.setId(rs.getInt("announcement_id"));
                a.setTitle(rs.getString("title"));
                a.setContent(rs.getString("content"));
                a.setEventDate(rs.getDate("event_date"));
                a.setLocation(rs.getString("location"));
                a.setPostedBy(rs.getInt("posted_by"));
                list.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // --- 5️⃣ Event Registrations ---
    public List<EventRegistration> getEventRegistrations(int eventId) {
        List<EventRegistration> list = new ArrayList<>();
        String sql = "SELECT * FROM event_registrations WHERE event_id=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EventRegistration er = new EventRegistration();
                er.setRegistrationId(rs.getInt("registration_id"));
                er.setEventId(rs.getInt("event_id"));
                er.setUserId(rs.getInt("user_id"));
                er.setRegistrationStatus(rs.getString("registration_status"));
                er.setRegisteredAt(rs.getTimestamp("registered_at"));
                list.add(er);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // --- 6️⃣ Waitlist Management ---
    public List<EventWaitlist> getWaitlist(int eventId) {
        List<EventWaitlist> list = new ArrayList<>();
        String sql = "SELECT * FROM event_waitlist WHERE event_id=? ORDER BY position";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EventWaitlist ew = new EventWaitlist();
                ew.setWaitlistId(rs.getInt("waitlist_id"));
                ew.setEventId(rs.getInt("event_id"));
                ew.setUserId(rs.getInt("user_id"));
                ew.setPosition(rs.getInt("position"));
                ew.setAddedAt(rs.getTimestamp("added_at"));
                list.add(ew);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean removeFromWaitlist(int waitlistId) {
        String sql = "DELETE FROM event_waitlist WHERE waitlist_id=?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, waitlistId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean moveFromWaitlistToRegistration(int waitlistId, int eventId, int userId) {
        // Remove from waitlist
        if (!removeFromWaitlist(waitlistId))
            return false;

        // Add to registration
        String sql = "INSERT INTO event_registrations (event_id, user_id) VALUES (?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, eventId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
