package com.vms.model;

import java.sql.Date;
import java.sql.Time;

public class Lecture {
    private long lectureId;
    private String title;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(long volunteerId) {
        this.volunteerId = volunteerId;
    }

    private String description;
    private Date lectureDate;
    private Time startTime;
    private Time endTime;
    private String location;
    private String status;
    private long volunteerId;

    private String instructor;

    // Constructor
    public Lecture() {}

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    // Ensure you have these for the DAO to work
    public void setLectureId(long lectureId) { this.lectureId = lectureId; }
    public long getLectureId() { return lectureId; }

    public void setTitle(String title) { this.title = title; }
    public String getTitle() { return title; }

    public void setLectureDate(Date lectureDate) { this.lectureDate = lectureDate; }
    public Date getLectureDate() { return lectureDate; }
}