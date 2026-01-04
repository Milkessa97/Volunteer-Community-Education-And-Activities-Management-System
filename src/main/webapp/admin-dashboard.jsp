<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard | Community Service</title>
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
                    <h1 class="text-xl font-bold tracking-tight">Community Service <span class="text-blue-600 font-medium text-sm ml-2 px-2 py-1 bg-blue-50 rounded-full">Admin</span></h1>
                </div>

                <div class="flex items-center gap-4">
                    <div class="text-right hidden sm:block">
                        <p class="text-sm font-semibold">
                            <c:out value="${sessionScope.currentUser.fullName}" default="Admin User" />
                        </p>
                        <p class="text-xs text-gray-500 uppercase tracking-wider">System Management</p>
                    </div>
                    <form action="logout" method="post">
                        <button type="submit" class="inline-flex items-center gap-2 border border-gray-300 bg-white hover:bg-gray-50 h-9 px-4 rounded-md text-sm font-medium transition-all shadow-sm">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                                <polyline points="16 17 21 12 16 7"></polyline>
                                <line x1="21" x2="9" y1="12" y2="12"></line>
                            </svg>
                            Logout
                        </button>
                    </form>
                </div>
            </div>
        </header>

        <main class="container mx-auto px-4 py-8 max-w-6xl">
            <div class="mb-10 flex flex-col md:flex-row md:items-center md:justify-between gap-4">
                <div>
                    <h1 class="text-4xl font-extrabold text-gray-900 mb-2">Admin Control Center</h1>
                    <p class="text-gray-600 text-lg">Managing the system and tracking engagement.</p>
                </div>
                <a href="admin?action=createLecturePage" class="inline-flex items-center justify-center bg-blue-600 text-white h-12 px-8 rounded-xl font-bold hover:bg-blue-700 transition-all shadow-lg shadow-blue-200">
                    + Create New Lecture
                </a>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-10">
                <div class="bg-white rounded-xl border border-gray-200 p-6 shadow-sm hover:shadow-md transition-shadow">
                    <div class="flex items-center justify-between">
                        <div class="space-y-1">
                            <p class="text-sm text-gray-500 font-medium">Upcoming</p>
                            <p class="text-3xl font-bold text-gray-900">${upcomingCount}</p>
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
                            <p class="text-3xl font-bold text-gray-900">${completed}</p>
                        </div>
                        <div class="p-3 rounded-lg bg-green-50 text-green-600">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"></circle><path d="m9 12 2 2 4-4"></path></svg>
                        </div>
                    </div>
                </div>

                <div class="bg-white rounded-xl border border-gray-200 p-6 shadow-sm hover:shadow-md transition-shadow">
                    <div class="flex items-center justify-between">
                        <div class="space-y-1">
                            <p class="text-sm text-gray-500 font-medium">Total Attendees</p>
                            <p class="text-3xl font-bold text-gray-900">${totalAttendees}</p>
                        </div>
                        <div class="p-3 rounded-lg bg-purple-50 text-purple-600">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"></path><circle cx="9" cy="7" r="4"></circle><path d="M22 21v-2a4 4 0 0 0-3-3.87"></path><path d="M16 3.13a4 4 0 0 1 0 7.75"></path></svg>
                        </div>
                    </div>
                </div>

                <div class="bg-white rounded-xl border border-gray-200 p-6 shadow-sm hover:shadow-md transition-shadow">
                    <div class="flex items-center justify-between">
                        <div class="space-y-1">
                            <p class="text-sm text-gray-500 font-medium">Avg. Attendance</p>
                            <p class="text-3xl font-bold text-gray-900">${averageAttendees}</p>
                        </div>
                        <div class="p-3 rounded-lg bg-orange-50 text-orange-600">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="12" x2="12" y1="20" y2="10"></line><line x1="18" x2="18" y1="20" y2="4"></line><line x1="6" x2="6" y1="20" y2="16"></line></svg>
                        </div>
                    </div>
                </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-8 mb-8">
                <div class="bg-white border border-gray-200 rounded-2xl p-8 hover:shadow-lg transition-all group">
                    <div class="flex items-start justify-between mb-6">
                        <div>
                            <h3 class="text-2xl font-bold text-gray-900 mb-2">Manage Lectures</h3>
                            <p class="text-gray-500 leading-relaxed">Full control over scheduling, modifications, and attendance auditing for all sessions.</p>
                        </div>
                        <div class="p-4 bg-blue-600 text-white rounded-2xl shadow-blue-200 shadow-lg group-hover:scale-110 transition-transform">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 12V7a2 2 0 0 0-2-2H5a2 2 0 0 0-2 2v10a2 2 0 0 0 2 2h7"></path><line x1="3" x2="21" y1="10" y2="10"></line><path d="m16 19 2 2 4-4"></path></svg>
                        </div>
                    </div>
                    <a href="admin?action=lectures" class="flex items-center justify-center bg-blue-600 text-white h-12 rounded-xl font-bold hover:bg-blue-700 transition-colors shadow-md">
                        View Lectures
                    </a>
                </div>

                <div class="bg-white border border-gray-200 rounded-2xl p-8 hover:shadow-lg transition-all group overflow-hidden relative">
                    <div class="flex items-start justify-between mb-6 relative z-10">
                        <div>
                            <h3 class="text-2xl font-bold text-gray-900 mb-2">Popular Highlight</h3>
                            <p class="text-gray-500 leading-relaxed font-medium">Top Performer: <span class="text-blue-600 underline">${popularLecture.title}</span></p>
                            <p class="text-sm text-gray-400 mt-1">Instructor: ${popularLecture.instructor}</p>
                        </div>
                        <div class="p-4 bg-gray-900 text-white rounded-2xl shadow-gray-200 shadow-lg group-hover:scale-110 transition-transform">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M6 9H4.5a2.5 2.5 0 0 1 0-5H6"></path><path d="M18 9h1.5a2.5 2.5 0 0 0 0-5H18"></path><path d="M4 22h16"></path><path d="M10 14.66V17c0 .55-.47.98-.97 1.21C7.85 18.75 7 20.24 7 22"></path><path d="M14 14.66V17c0 .55.47.98.97 1.21C16.15 18.75 17 20.24 17 22"></path><path d="M18 2H6v7a6 6 0 0 0 12 0V2Z"></path></svg>
                        </div>
                    </div>
                    <div class="absolute -bottom-4 -right-4 w-32 h-32 bg-gray-50 rounded-full z-0 group-hover:scale-150 transition-transform duration-500"></div>
                </div>
            </div>
        </main>
    </div>

</body>
</html>