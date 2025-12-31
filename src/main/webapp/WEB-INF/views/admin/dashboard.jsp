Mar, [12/22/25 10:56 AM]
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body>
<div id="root">

    <div class="min-h-screen bg-background">

        <!-- HEADER -->
        <header class="border-b border-border bg-card sticky top-0 z-50">
            <div class="container mx-auto px-4 py-4 flex items-center justify-between">

                <div class="flex items-center gap-2">
                    <!-- icon unchanged -->
                    <h1 class="text-xl font-heading font-semibold text-foreground">
                        Community Service
                    </h1>
                </div>

                <div class="flex items-center gap-4">
                    <div class="text-right hidden sm:block">
                        <p class="text-sm font-medium text-foreground">
                            ${sessionScope.admin.fullName}
                        </p>
                        <p class="text-xs text-muted-foreground capitalize">
                            ${sessionScope.admin.role}
                        </p>
                    </div>

                    <a href="logout"
                       class="inline-flex items-center justify-center gap-2 border border-input bg-background hover:bg-accent h-9 rounded-md px-3">
                        Logout
                    </a>
                </div>

            </div>
        </header>

        <!-- MAIN -->
        <main class="container mx-auto px-4 py-8">

            <div class="mb-8 flex items-center justify-between">
                <div>
                    <h1 class="text-4xl font-heading font-bold text-foreground mb-2">
                        Admin Dashboard
                    </h1>
                    <p class="text-muted-foreground text-lg">
                        System overview and management
                    </p>
                </div>

                <a href="admin?action=createLecture"
                   class="bg-primary text-primary-foreground h-11 rounded-md px-8 flex items-center">
                    Create Lecture
                </a>
            </div>

            <!-- STATS -->
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">

                <div class="rounded-lg border bg-card p-6">
                    <p class="text-sm text-muted-foreground">Upcoming Sessions</p>
                    <p class="text-3xl font-bold">${upcomingSessions}</p>
                </div>

                <div class="rounded-lg border bg-card p-6">
                    <p class="text-sm text-muted-foreground">Completed Sessions</p>
                    <p class="text-3xl font-bold">${completedSessions}</p>
                </div>

                <div class="rounded-lg border bg-card p-6">
                    <p class="text-sm text-muted-foreground">Total Attendees</p>
                    <p class="text-3xl font-bold">${totalAttendees}</p>
                </div>

                <div class="rounded-lg border bg-card p-6">
                    <p class="text-sm text-muted-foreground">Avg. per Lecture</p>
                    <p class="text-3xl font-bold">${averageAttendance}</p>
                </div>

            </div>

            <!-- MANAGE LECTURES -->
            <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">

                <div class="bg-card border rounded-xl p-6">
                    <h3 class="text-xl font-semibold mb-2">Manage Lectures</h3>
                    <p class="text-sm text-muted-foreground mb-4">
                        View, edit, or delete lectures
                    </p>

                    <a href="admin?action=pendingLectures"
                       class="bg-primary text-primary-foreground h-11 rounded-md px-8 flex items-center justify-center">
                        View All Lectures
                    </a>
                </div>

                <div class="bg-gradient-to-br from-primary/10 to-blue-500/10 border rounded-xl p-6">
                    <h3 class="text-lg font-semibold mb-3">
                        🏆 Most

Mar, [12/22/25 10:56 AM]
Popular Lecture
                    </h3>

                    <p class="font-medium">${popularLecture.title}</p>
                    <p class="text-sm text-muted-foreground">
                        by ${popularLecture.speaker}
                    </p>

                    <p class="text-sm mt-3">
                        ${popularLecture.attendees} attendees
                    </p>
                </div>

            </div>

            <!-- QUICK STATS -->
            <div class="bg-accent border rounded-xl p-6">
                <h3 class="text-lg font-semibold mb-3">Quick Stats</h3>

                <div class="grid grid-cols-1 md:grid-cols-3 gap-4">

                    <div class="bg-card p-4 rounded-lg">
                        <p class="text-sm text-muted-foreground">Total Lectures</p>
                        <p class="text-2xl font-bold">${totalLectures}</p>
                    </div>

                    <div class="bg-card p-4 rounded-lg">
                        <p class="text-sm text-muted-foreground">Active Volunteers</p>
                        <p class="text-2xl font-bold">${activeVolunteers}</p>
                    </div>

                    <div class="bg-card p-4 rounded-lg">
                        <p class="text-sm text-muted-foreground">Registered Students</p>
                        <p class="text-2xl font-bold">${registeredStudents}</p>
                    </div>

                </div>
            </div>

        </main>
    </div>
</div>
</body>