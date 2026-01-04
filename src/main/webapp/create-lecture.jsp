<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <title>Create New Lecture - VMS</title>
            <script src="https://cdn.tailwindcss.com"></script>
        </head>

        <body class="min-h-screen bg-slate-50">
            <header class="border-b border-slate-200 bg-white sticky top-0 z-50">
                <div class="container mx-auto px-4 py-4 flex items-center justify-between">
                    <div class="flex items-center gap-2">
                        <h1 class="text-xl font-semibold text-slate-900">Community Service</h1>
                    </div>
                    <div class="flex items-center gap-4">
                        <div class="text-right hidden sm:block">
                            <p class="text-sm font-medium text-slate-900">
                                <c:out value="${sessionScope.user.fullName}" default="Admin User" />
                            </p>
                            <p class="text-xs text-slate-500 capitalize">admin</p>
                        </div>
                        <form action="logout" method="post">
                            <button type="submit"
                                class="inline-flex items-center justify-center gap-2 text-sm font-medium border border-slate-200 bg-white hover:bg-slate-50 h-9 rounded-md px-3">
                                Logout
                            </button>
                        </form>
                    </div>
                </div>
            </header>
            <main class="container mx-auto px-4 py-8 max-w-3xl">
                <a href="admin?action=dashboard"
                    class="inline-flex items-center gap-2 text-sm font-medium text-slate-500 hover:text-blue-600 transition-colors mb-6">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none"
                        stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="m12 19-7-7 7-7" />
                        <path d="M19 12H5" />
                    </svg>
                    Back to Dashboard
                </a>

                <c:if test="${not empty errorMessage}">
                    <div class="mb-6 p-4 bg-red-50 border border-red-200 text-red-600 rounded-md text-sm">
                        ${errorMessage}
                    </div>
                </c:if>

                <div class="rounded-xl border border-slate-200 bg-white shadow-sm">
                    <div class="p-8 pb-2 border-b border-slate-100">
                        <h3 class="text-3xl font-bold text-slate-900">Create New Lecture</h3>
                        <p class="text-slate-500 mt-1">Fill in the details below to schedule a new volunteer-led
                            session.</p>
                    </div>

                    <div class="p-8 pt-2">
                        <form action="admin" method="post" class="space-y-6">
                            <input type="hidden" name="action" value="createLecture">

                            <div class="space-y-2">
                                <label class="text-sm font-semibold text-slate-700" for="title">Lecture Title *</label>
                                <input type="text" name="title" id="title" required
                                    placeholder="e.g., Advanced First Aid Workshop"
                                    class="flex h-11 w-full rounded-md border border-slate-200 bg-slate-100 px-3 py-2 text-sm ring-offset-white placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:bg-white transition-all">
                            </div>

                            <div class="space-y-2">
                                <label class="text-sm font-semibold text-slate-700" for="description">Description
                                    *</label>
                                <textarea name="description" id="description" rows="4" required
                                    placeholder="Provide a brief overview of what the volunteers will learn..."
                                    class="flex min-h-[100px] w-full rounded-md border border-slate-200 bg-slate-100 px-3 py-2 text-sm placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:bg-white transition-all"></textarea>
                            </div>

                            <div class="space-y-2">
                                <label class="text-sm font-semibold text-slate-700" for="volunteerId">Assigned Lecturer
                                    *</label>
                                <select name="volunteerId" id="volunteerId" required
                                    class="flex h-11 w-full rounded-md border border-slate-200 bg-slate-100 px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:bg-white transition-all">
                                    <option value="" disabled selected>Select a volunteer from the list</option>
                                    <c:choose>
                                        <c:when test="${empty volunteers}">
                                            <option value="" disabled>No volunteers found in the database</option>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="v" items="${volunteers}">
                                                <option value="${v.userId}">${v.fullName}</option>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </div>

                            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                                <div class="space-y-2">
                                    <label class="text-sm font-semibold text-slate-700" for="date">Date *</label>
                                    <input type="date" name="date" id="date" required
                                        class="flex h-11 w-full rounded-md border border-slate-200 bg-slate-100 px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:bg-white transition-all">
                                </div>
                                <div class="space-y-2">
                                    <label class="text-sm font-semibold text-slate-700" for="time">Start Time *</label>
                                    <input type="time" name="time" id="time" required
                                        class="flex h-11 w-full rounded-md border border-slate-200 bg-slate-100 px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 focus:bg-white transition-all">
                                </div>
                            </div>

                            <div class="space-y-2">
                                <label class="text-sm font-semibold text-slate-700" for="location">Location *</label>
                                <input type="text" name="location" id="location" required
                                    placeholder="e.g., Main Hall, Community Center"
                                    class="flex h-11 w-full rounded-md border border-slate-200 bg-slate-100 px-3 py-2 text-sm placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:bg-white transition-all">
                            </div>

                            <div class="space-y-2">
                                <label class="text-sm font-semibold text-slate-700" for="maxAttendees">Max
                                    Attendees</label>
                                <input type="number" name="maxAttendees" id="maxAttendees" placeholder="e.g., 25"
                                    class="flex h-11 w-full rounded-md border border-slate-200 bg-slate-100 px-3 py-2 text-sm placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:bg-white transition-all">
                            </div>

                            <div class="flex items-center gap-4 pt-6">
                                <button type="submit"
                                    class="flex-1 inline-flex items-center justify-center rounded-md bg-blue-600 px-8 py-3 text-sm font-bold text-white shadow transition-colors hover:bg-blue-700 focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-blue-500">
                                    Create Lecture
                                </button>
                                <a href="admin?action=dashboard"
                                    class="inline-flex items-center justify-center rounded-md border border-slate-200 bg-white px-8 py-3 text-sm font-semibold text-slate-700 shadow-sm hover:bg-slate-50 transition-colors">
                                    Cancel
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </body>

        </html>