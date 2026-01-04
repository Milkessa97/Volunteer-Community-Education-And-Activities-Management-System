<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard | Community Service</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 text-gray-900 font-sans">

<div class="min-h-screen">
    <header class="border-b border-gray-200 bg-white sticky top-0 z-50">
        <div class="container mx-auto px-4 py-4 flex items-center justify-between">
            <div class="flex items-center gap-2">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="w-6 h-6 text-blue-600">
                    <path d="M12 7v14"></path>
                    <path d="M3 18a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h5a4 4 0 0 1 4 4 4 4 0 0 1 4-4h5a1 1 0 0 1 1 1v13a1 1 0 0 1-1 1h-6a3 3 0 0 0-3 3 3 3 0 0 0-3-3z"></path>
                </svg>
                <h1 class="text-xl font-bold tracking-tight">Community Service</h1>
            </div>

            <div class="flex items-center gap-4">
                <div class="text-right hidden sm:block">
                    <p class="text-sm font-semibold">
                        <c:out value="${sessionScope.currentUser.fullName}" default="Student" />
                    </p>
                    <p class="text-xs text-gray-500 uppercase tracking-wider">Student Account</p>
                </div>
                <a href="logout" class="inline-flex items-center gap-2 border border-gray-300 bg-white hover:bg-gray-50 h-9 px-4 rounded-md text-sm font-medium transition-all shadow-sm">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                        <polyline points="16 17 21 12 16 7"></polyline>
                        <line x1="21" x2="9" y1="12" y2="12"></line>
                    </svg>
                    Logout
                </a>
            </div>
        </div>
    </header>

    <main class="container mx-auto px-4 py-8 max-w-6xl">
        <div class="mb-10">
            <h1 class="text-4xl font-extrabold text-gray-900 mb-2">
                Welcome back, <c:out value="${sessionScope.currentUser.fullName}" default="Student" />!
            </h1>
            <p class="text-gray-600 text-lg">Ready to explore new learning opportunities today?</p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-10">
            <div class="bg-white rounded-xl border border-gray-200 p-6 shadow-sm hover:shadow-md transition-shadow">
                <div class="flex items-center justify-between">
                    <div class="space-y-1">
                        <p class="text-sm text-gray-500 font-medium">Registered Lectures</p>
                        <p class="text-3xl font-bold text-gray-900">
                            <c:out value="${myLecturesCount != null ? myLecturesCount : 0}" />
                        </p>
                    </div>
                    <div class="p-3 rounded-lg bg-blue-50 text-blue-600">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M4 19.5v-15A2.5 2.5 0 0 1 6.5 2H20v20H6.5a2.5 2.5 0 0 1-2.5-2.5Z"></path><path d="M8 7h6"></path><path d="M8 11h8"></path></svg>
                    </div>
                </div>
            </div>

            <div class="bg-white rounded-xl border border-gray-200 p-6 shadow-sm hover:shadow-md transition-shadow">
                <div class="flex items-center justify-between">
                    <div class="space-y-1">
                        <p class="text-sm text-gray-500 font-medium">Upcoming Sessions</p>
                        <p class="text-3xl font-bold text-gray-900">
                            <c:out value="${upcomingSessions != null ? upcomingSessions : 0}" />
                        </p>
                    </div>
                    <div class="p-3 rounded-lg bg-blue-50 text-blue-600">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect width="18" height="18" x="3" y="4" rx="2" ry="2"></rect><line x1="16" x2="16" y1="2" y2="6"></line><line x1="8" x2="8" y1="2" y2="6"></line><line x1="3" x2="21" y1="10" y2="10"></line></svg>
                    </div>
                </div>
            </div>

            <div class="bg-white rounded-xl border border-gray-200 p-6 shadow-sm hover:shadow-md transition-shadow">
                <div class="flex items-center justify-between">
                    <div class="space-y-1">
                        <p class="text-sm text-gray-500 font-medium">Completed</p>
                        <p class="text-3xl font-bold text-gray-900">
                            <c:out value="${completedSessions != null ? completedSessions : 0}" />
                        </p>
                    </div>
                    <div class="p-3 rounded-lg bg-green-50 text-green-600">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"></circle><path d="m9 12 2 2 4-4"></path></svg>
                    </div>
                </div>
            </div>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div class="bg-white border border-gray-200 rounded-2xl p-8 hover:shadow-lg transition-all group">
                <div class="flex items-start justify-between mb-6">
                    <div>
                        <h3 class="text-2xl font-bold text-gray-900 mb-2">Browse Lectures</h3>
                        <p class="text-gray-500 leading-relaxed">Discover fresh topics and register for new upcoming sessions happening this week.</p>
                    </div>
                    <div class="p-4 bg-blue-600 text-white rounded-2xl shadow-blue-200 shadow-lg group-hover:scale-110 transition-transform">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="22 7 13.5 15.5 8.5 10.5 2 17"></polyline><polyline points="16 7 22 7 22 13"></polyline></svg>
                    </div>
                </div>
                <a href="student?action=lectures" class="flex items-center justify-center bg-blue-600 text-white h-12 rounded-xl font-bold hover:bg-blue-700 transition-colors shadow-md">
                    View Available Lectures
                </a>
            </div>

            <div class="bg-white border border-gray-200 rounded-2xl p-8 hover:shadow-lg transition-all group">
                <div class="flex items-start justify-between mb-6">
                    <div>
                        <h3 class="text-2xl font-bold text-gray-900 mb-2">My Schedule</h3>
                        <p class="text-gray-500 leading-relaxed">Keep track of your current registrations, view session dates, and check your status.</p>
                    </div>
                    <div class="p-4 bg-gray-900 text-white rounded-2xl shadow-gray-200 shadow-lg group-hover:scale-110 transition-transform">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"></circle><path d="m9 12 2 2 4-4"></path></svg>
                    </div>
                </div>
                <a href="student?action=mySchedule" class="flex items-center justify-center border-2 border-gray-200 text-gray-900 h-12 rounded-xl font-bold hover:bg-gray-50 transition-colors shadow-sm">
                    View My Registrations
                </a>
            </div>
        </div>
    </main>
</div>

</body>
</html>