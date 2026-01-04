# Volunteer Management System (VMS)

A Java-based web application designed to facilitate community service and volunteer management. This system allows administrators to manage lectures/events, volunteers to sign up and view their schedules, and students/attendees to browse and register for sessions.

## Team Members
*   **Eyob Bahiru**
*   **Maramawit Esualegn**
*   **Milkessa Habtamu**
*   **Semret Adnew**
*   **Ruth Alemu**

## Project Overview

The VMS application provides role-based access control for three main user types:
1.  **Admin**: Full control over lectures, events, announcements, and user management.
2.  **Volunteer**: Can view assigned lectures and manage their schedule.
3.  **Student/User**: Can browse available lectures and register for them.

### Key Features
*   **User Authentication**: Secure login with role-based redirection.
*   **Lecture Management**: Create, edit, delete, and approve lectures.
*   **Event Waitlists**: Manage capacity and waitlists for popular events.
*   **Dashboard**: Customized dashboards for Admins, Volunteers, and Students with relevant metrics.
*   **Responsive UI**: Built with Tailwind CSS for a modern, mobile-friendly interface.

## Technology Stack
*   **Backend**: Java Servlets, JSP (JavaServer Pages)
*   **Database**: Relational Database (MySQL/PostgreSQL) via JDBC
*   **Frontend**: HTML5, Tailwind CSS
*   **Build Tool**: Maven

## Project File Structure

```
AP Project
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.vms
│   │   │       ├── dao         # Data Access Objects (DB interaction)
│   │   │       ├── filter      # Servlet filters (Authentication/AuthZ)
│   │   │       ├── model       # Data Models (POJOs)
│   │   │       ├── servlet     # Servlet Controllers (Business Logic)
│   │   │       └── util        # Utilities (DB Connection, etc.)
│   │   │
│   │   └── webapp              # Web Content
│   │       ├── admin-dashboard.jsp
│   │       ├── browse-lectures.jsp
│   │       ├── create-lecture.jsp
│   │       ├── login.jsp
│   │       ├── manage-lectures.jsp
│   │       ├── student-dashboard.jsp
│   │       ├── student-mySchedule.jsp
│   │       ├── volunteer-dashboard.jsp
│   │       ├── volunteer-myLectures.jsp
│   │       └── WEB-INF
│   │           └── web.xml
│   │
└── pom.xml                     # Maven Dependencies
```

## Setup and Installation

1.  **Prerequisites**:
    *   JDK 21 or higher (configured in pom.xml)
    *   Apache Maven
    *   Tomcat 10 or similar Servlet Container (Uses Jakarta EE 10)
    *   MySQL Database

2.  **Database Configuration**:
    *   Update database credentials in `src/main/java/com/vms/util/DBUtil.java`.

3.  **Build**:
    ```bash
    mvn clean install
    ```

4.  **Run**:
    *   Deploy the resulting `.war` file to your Tomcat `webapps` directory.
    *   Or use a plugin like Smart Tomcat in IntelliJ IDEA.

5.  **Access**:
    *   Navigate to `http://localhost:8080/vms` (or your configured context path).
