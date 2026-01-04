package com.vms.dao;

import com.vms.model.Lecture;
import com.vms.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {


    public List<Lecture> getAvailableLectures() {
        List<Lecture> lectures = new ArrayList<>();

        String sql = "SELECT l.*, u.full_name AS instructor_name " +
                "FROM lectures l " +
                "JOIN users u ON l.volunteer_id = u.user_id " +
                "WHERE l.status = 'PENDING'";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lectures.add(mapRowToLecture(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lectures;
    }


    public boolean registerForLecture(int studentId, int lectureId) {
        String sql = "INSERT INTO lecture_registrations (student_id, lecture_id) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, lectureId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            if (e.getErrorCode() == 1062) {
                System.out.println("Student already registered for this lecture.");
            }
            e.printStackTrace();
            return false;
        }
    }


    public List<Lecture> getStudentRegistrations(int studentId) {
        List<Lecture> myLectures = new ArrayList<>();
        String sql = "SELECT l.*, u.full_name AS instructor_name " +
                "FROM lectures l " +
                "JOIN lecture_registrations r ON l.lecture_id = r.lecture_id " +
                "JOIN users u ON l.volunteer_id = u.user_id " +
                "WHERE r.student_id = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    myLectures.add(mapRowToLecture(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myLectures;
    }


    public int countUpcomingForStudent(int studentId) {
        String sql = "SELECT COUNT(*) FROM lecture_registrations lr " +
                "JOIN lectures l ON lr.lecture_id = l.lecture_id " +
                "WHERE lr.student_id = ? AND l.lecture_date >= CURRENT_DATE";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int countCompletedForStudent(int studentId) {
        String sql = "SELECT COUNT(*) FROM lecture_registrations lr " +
                "JOIN lectures l ON lr.lecture_id = l.lecture_id " +
                "WHERE lr.student_id = ? AND l.lecture_date < CURRENT_DATE";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public boolean isStudentRegistered(int studentId, int lectureId) {
        String sql = "SELECT COUNT(*) FROM lecture_registrations WHERE student_id = ? AND lecture_id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, lectureId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Lecture mapRowToLecture(ResultSet rs) throws SQLException {
        Lecture l = new Lecture();
        l.setLectureId(rs.getInt("lecture_id"));
        l.setTitle(rs.getString("title"));
        l.setDescription(rs.getString("description"));
        l.setLectureDate(rs.getDate("lecture_date"));
        l.setStartTime(rs.getTime("start_time"));
        l.setEndTime(rs.getTime("end_time"));
        l.setLocation(rs.getString("location"));
        l.setVolunteerId(rs.getLong("volunteer_id"));
        l.setStatus(rs.getString("status"));


        try {
            l.setInstructor(rs.getString("instructor_name"));
        } catch (SQLException e) {
        }
        return l;
    }
}