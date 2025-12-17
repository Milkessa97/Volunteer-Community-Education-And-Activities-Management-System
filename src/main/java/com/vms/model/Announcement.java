package com.vms.model;

import java.sql.Date;

public class Announcement {
    private int id;
    private String title;
    private String content;
    private Date eventDate;
    private String location;
    private int postedBy;

    public Announcement() {
    }

    public Announcement(int id, String title, String content, Date eventDate, String location, int postedBy) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.eventDate = eventDate;
        this.location = location;
        this.postedBy = postedBy;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(int postedBy) {
        this.postedBy = postedBy;
    }
}
