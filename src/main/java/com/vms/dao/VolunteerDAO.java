package com.vms.dao;

import com.vms.model.*;
import com.vms.util.DBUtil; // Assuming you have a utility to get connections
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VolunteerDAO {

    // Count Assigned Lectures (Total)
    public int countAssignedLectures(long volunteerId) {
        String sql = "SELECT COUNT(*) FROM lectures WHERE volunteer_id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, volunteerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Count Total Students Reached
    // This joins the registrations table with lectures to count unique student
    // sign-ups
    public int countTotalStudentsReached(long volunteerId) {
        String sql = "SELECT COUNT(r.id) FROM registrations r " +
                "JOIN lectures l ON r.lecture_id = l.id " +
                "WHERE l.volunteer_id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, volunteerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Count Upcoming Sessions (Lectures scheduled for the future)
    public int countUpcomingSessions(long volunteerId) {
        String sql = "SELECT COUNT(*) FROM lectures WHERE volunteer_id = ? AND lecture_date >= CURRENT_DATE";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, volunteerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 4. Get List of Assigned Lectures
    public List<Lecture> getLecturesByVolunteer(long volunteerId) {
        List<Lecture> list = new ArrayList<>();
        String sql = "SELECT * FROM lectures WHERE volunteer_id = ? ORDER BY lecture_date DESC";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, volunteerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToLecture(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Helper method to map SQL rows to Java Objects
    private Lecture mapResultSetToLecture(ResultSet rs) throws SQLException {
        Lecture l = new Lecture();
        l.setLectureId(rs.getLong("lecture_id"));
        l.setTitle(rs.getString("title"));
        l.setDescription(rs.getString("description"));
        l.setLectureDate(rs.getDate("lecture_date"));
        l.setStartTime(rs.getTime("start_time"));
        l.setEndTime(rs.getTime("end_time"));
        l.setLocation(rs.getString("location"));
        l.setStatus(rs.getString("status"));
        return l;
    }

    // Get Upcoming Schedule (Future events only)
    public List<Lecture> getVolunteerSchedule(long volunteerId) {
        List<Lecture> schedule = new ArrayList<>();
        // Fetches only lectures happening today or in the future
        String sql = "SELECT * FROM lectures " +
                "WHERE volunteer_id = ? AND lecture_date >= CURRENT_DATE " +
                "ORDER BY lecture_date ASC, start_time ASC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, volunteerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                schedule.add(mapResultSetToLecture(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedule;
    }
}