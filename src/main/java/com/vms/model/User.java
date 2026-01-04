package com.vms.model;

public class User {
    private int userId;
    private String fullName;
    private String username;
    private String password;
    private long roleId;

    public enum Role {
        ADMIN, VOLUNTEER, STUDENT
    }

    public Role getRole() {
        // Mapping based on your roles table: 1=Admin, 2=Volunteer, 3=Student
        if (this.roleId == 1) return Role.ADMIN;
        if (this.roleId == 2) return Role.VOLUNTEER;
        return Role.STUDENT;
    }
    // Constructors, Getters, and Setters
    public User() {}


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}