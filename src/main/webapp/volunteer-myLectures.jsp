<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Volunteer Dashboard | Community Service</title>
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
                            <h1 class="text-xl font-bold tracking-tight">Community Service
                                <span
                                    class="text-blue-600 font-medium text-sm ml-2 px-2 py-1 bg-blue-50 rounded-full">Volunteer</span>
                            </h1>
                        </div>

                        <div class="flex items-center gap-4">
                            <div class="text-right hidden sm:block">
                                <p class="text-sm font-semibold">${sessionScope.currentUser.fullName}</p>
                                <p class="text-xs text-gray-500 uppercase tracking-wider">Volunteer</p>
                            </div>
                            <form action="logout" method="post">
                                <button type="submit"
                                    class="inline-flex items-center gap-2 border border-gray-300 bg-white hover:bg-gray-50 h-9 px-4 rounded-md text-sm font-medium transition-all shadow-sm">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                        fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                        stroke-linejoin="round" class="w-4 h-4">
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
                    <div class="mb-8">
                        <h1 class="text-4xl font-extrabold text-gray-900 mb-2">My Assigned Lectures</h1>
                        <p class="text-gray-600 text-lg">View your lectures and manage registered students</p>
                    </div>

                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        <c:forEach var="lecture" items="${assignedLectures}">
                            <div
                                class="bg-white rounded-xl border border-gray-200 shadow-sm hover:shadow-lg transition-all duration-300 overflow-hidden flex flex-col">
                                <div class="p-6 flex-1">
                                    <div class="flex justify-between items-start mb-4">
                                        <h3 class="font-bold text-xl text-gray-900">${lecture.title}</h3>

                                        <jsp:useBean id="now" class="java.util.Date" />
                                        <c:choose>
                                            <c:when test="${lecture.status eq 'APPROVED'}">
                                                <span
                                                    class="px-2.5 py-0.5 rounded-full text-xs font-bold uppercase tracking-wider bg-blue-50 text-blue-600">
                                                    Approved
                                                </span>
                                            </c:when>
                                            <c:when test="${lecture.lectureDate lt now}">
                                                <span
                                                    class="px-2.5 py-0.5 rounded-full text-xs font-bold uppercase tracking-wider bg-green-50 text-green-600">
                                                    Completed
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span
                                                    class="px-2.5 py-0.5 rounded-full text-xs font-bold uppercase tracking-wider bg-gray-100 text-gray-600">
                                                    Upcoming
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <p class="text-sm text-blue-600 mb-4 font-medium">by ${lecture.instructor}</p>
                                    <p class="text-sm text-gray-600 line-clamp-2 mb-6">${lecture.description}</p>

                                    <div class="space-y-3">
                                        <div class="flex items-center gap-3 text-sm text-gray-500">
                                            <svg class="w-4 h-4 text-blue-600" fill="none" stroke="currentColor"
                                                viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                    d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z">
                                                </path>
                                            </svg>
                                            ${lecture.lectureDate}
                                        </div>
                                        <div class="flex items-center gap-3 text-sm text-gray-500">
                                            <svg class="w-4 h-4 text-blue-600" fill="none" stroke="currentColor"
                                                viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                    d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                                            </svg>
                                            ${lecture.startTime}
                                        </div>
                                        <div class="flex items-center gap-3 text-sm text-gray-500">
                                            <svg class="w-4 h-4 text-blue-600" fill="none" stroke="currentColor"
                                                viewBox="0 0 24 24">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                    d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z">
                                                </path>
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                    d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path>
                                            </svg>
                                            ${lecture.location}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                        <c:if test="${empty assignedLectures}">
                            <div class="col-span-full py-20 text-center">
                                <p class="text-gray-400 text-lg">You have no assigned lectures yet.</p>
                            </div>
                        </c:if>
                    </div>
                </main>
            </div>

        </body>

        </html>