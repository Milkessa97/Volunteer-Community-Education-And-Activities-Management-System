<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Browse Lectures | Community Service</title>
            <script src="https://cdn.tailwindcss.com"></script>
        </head>

        <body class="bg-gray-50 text-gray-900 font-sans">

            <div class="min-h-screen">
                <header class="border-b border-gray-200 bg-white sticky top-0 z-50">
                    <div class="container mx-auto px-4 py-4 flex items-center justify-between">
                        <div class="flex items-center gap-2">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                                fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                stroke-linejoin="round" class="w-6 h-6 text-blue-600">
                                <path d="M12 7v14"></path>
                                <path
                                    d="M3 18a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h5a4 4 0 0 1 4 4 4 4 0 0 1 4-4h5a1 1 0 0 1 1 1v13a1 1 0 0 1-1 1h-6a3 3 0 0 0-3 3 3 3 0 0 0-3-3z">
                                </path>
                            </svg>
                            <h1 class="text-xl font-bold">Community Service</h1>
                        </div>
                        <div class="flex items-center gap-4">
                            <nav class="hidden md:flex gap-6 mr-6">
                                <a href="student?action=dashboard"
                                    class="text-sm font-medium text-gray-500 hover:text-blue-600">My Schedule</a>
                                <a href="student?action=browse" class="text-sm font-bold text-blue-600">Browse All</a>
                            </nav>
                            <div class="text-right hidden sm:block">
                                <p class="text-sm font-semibold">${sessionScope.currentUser.fullName}</p>
                                <p class="text-xs text-gray-400 uppercase">Student</p>
                            </div>
                        </div>
                    </div>
                </header>

                <main class="container mx-auto px-4 py-8 max-w-6xl">
                    <div class="mb-8">
                        <h1 class="text-4xl font-extrabold text-gray-900 mb-2">Available Lectures</h1>
                        <p class="text-gray-600 text-lg">Browse and register for upcoming community sessions</p>
                    </div>

                    <!-- Success Message -->
                    <c:if test="${not empty sessionScope.message}">
                        <div
                            class="mb-6 p-4 bg-green-50 border border-green-200 text-green-800 rounded-lg flex items-center gap-3">
                            <svg class="w-5 h-5 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                            </svg>
                            <span class="font-medium">${sessionScope.message}</span>
                        </div>
                        <c:remove var="message" scope="session" />
                    </c:if>

                    <!-- Error Message -->
                    <c:if test="${not empty sessionScope.error}">
                        <div
                            class="mb-6 p-4 bg-red-50 border border-red-200 text-red-800 rounded-lg flex items-center gap-3">
                            <svg class="w-5 h-5 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                            </svg>
                            <span class="font-medium">${sessionScope.error}</span>
                        </div>
                        <c:remove var="error" scope="session" />
                    </c:if>

                    <div class="mb-10">
                        <form action="student" method="GET" class="relative max-w-md">
                            <input type="hidden" name="action" value="browse">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24"
                                fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                stroke-linejoin="round" class="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400">
                                <circle cx="11" cy="11" r="8"></circle>
                                <path d="m21 21-4.3-4.3"></path>
                            </svg>
                            <input type="text" name="query" value="${param.query}"
                                class="w-full pl-10 pr-4 py-2 border border-gray-200 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition-all"
                                placeholder="Search topics or lecturers...">
                        </form>
                    </div>

                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        <c:forEach var="lecture" items="${availableLectures}">
                            <div
                                class="bg-white rounded-xl border border-gray-200 shadow-sm hover:shadow-md transition-all flex flex-col">
                                <div class="p-6 flex-1">
                                    <h3 class="font-bold text-xl mb-1 text-gray-900">${lecture.title}</h3>
                                    <p class="text-sm text-blue-600 font-medium mb-4">by ${lecture.instructor}</p>
                                    <p class="text-sm text-gray-600 line-clamp-2 mb-6">${lecture.description}</p>

                                    <div class="space-y-2 text-sm text-gray-500">
                                        <div class="flex items-center gap-2">
                                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path
                                                    d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z">
                                                </path>
                                            </svg>
                                            ${lecture.lectureDate}
                                        </div>
                                        <div class="flex items-center gap-2">
                                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path
                                                    d="M17 21v-2a4 4 0 00-4-4H5a4 4 0 00-4 4v2m16-10a4 4 0 010 8m-8-8a4 4 0 110 8">
                                                </path>
                                            </svg>
                                            Seats available
                                        </div>
                                    </div>
                                </div>

                                <div class="p-6 pt-0 mt-auto">
                                    <c:choose>
                                        <c:when test="${registrationStatus[lecture.lectureId]}">
                                            <button disabled
                                                class="w-full py-2.5 bg-gray-100 text-gray-500 rounded-lg font-bold flex items-center justify-center gap-2 cursor-not-allowed">
                                                <svg class="w-4 h-4" fill="none" stroke="currentColor"
                                                    viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round"
                                                        stroke-width="3" d="M5 13l4 4L19 7"></path>
                                                </svg>
                                                Registered
                                            </button>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="student" method="POST">
                                                <input type="hidden" name="action" value="register">
                                                <input type="hidden" name="lectureId" value="${lecture.lectureId}">
                                                <button type="submit"
                                                    class="w-full py-2.5 bg-blue-600 hover:bg-blue-700 text-white rounded-lg font-bold transition-colors shadow-sm shadow-blue-200">
                                                    Register Now
                                                </button>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </main>
            </div>

        </body>

        </html>