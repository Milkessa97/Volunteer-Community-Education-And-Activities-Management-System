<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Manage Lectures | Community Service</title>
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
                                <h1 class="text-xl font-bold tracking-tight">Community Service</h1>
                            </div>
                            <div class="flex items-center gap-4">
                                <a href="admin?action=dashboard"
                                    class="text-sm font-semibold text-gray-600 hover:text-blue-600 transition-colors">Dashboard</a>
                                <form action="logout" method="post" class="inline">
                                    <button type="submit"
                                        class="inline-flex items-center gap-2 border border-gray-300 bg-white hover:bg-gray-50 h-9 px-4 rounded-md text-sm font-medium transition-all shadow-sm">
                                        Logout
                                    </button>
                                </form>
                            </div>
                        </div>
                    </header>

                    <main class="container mx-auto px-4 py-8 max-w-6xl">
                        <div class="mb-10 flex flex-col md:flex-row md:items-center justify-between gap-6">
                            <div>
                                <h1 class="text-4xl font-extrabold text-gray-900 mb-2">Manage Lectures</h1>
                                <p class="text-gray-600 text-lg">Review, edit, and monitor volunteer sessions across the
                                    platform.</p>
                            </div>
                            <a href="admin?action=createLecturePage"
                                class="inline-flex items-center justify-center bg-blue-600 text-white h-12 px-8 rounded-xl font-bold hover:bg-blue-700 transition-all shadow-lg shadow-blue-200">
                                + Create New Lecture
                            </a>
                        </div>

                        <div
                            class="bg-white border border-gray-200 rounded-2xl p-6 mb-8 flex flex-col md:flex-row gap-4 shadow-sm">
                            <div class="relative flex-1">
                                <span class="absolute inset-y-0 left-0 pl-3 flex items-center text-gray-400">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24"
                                        fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                        stroke-linejoin="round">
                                        <circle cx="11" cy="11" r="8"></circle>
                                        <line x1="21" x2="16.65" y1="21" y2="16.65"></line>
                                    </svg>
                                </span>
                                <input type="text" id="searchInput" placeholder="Search by title or instructor..."
                                    class="w-full h-11 pl-10 pr-4 rounded-xl border border-gray-200 bg-gray-50 text-sm focus:bg-white focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition-all">
                            </div>
                            <div class="relative">
                                <select id="statusFilter"
                                    class="appearance-none h-11 pl-4 pr-10 rounded-xl border border-gray-200 bg-gray-50 text-sm font-medium focus:bg-white focus:ring-2 focus:ring-blue-500 outline-none cursor-pointer transition-all">
                                    <option value="">All Statuses</option>
                                    <option value="APPROVED">Approved</option>
                                    <option value="PENDING">Pending</option>
                                </select>
                                <span
                                    class="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none text-gray-400">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                                        fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                                        stroke-linejoin="round">
                                        <path d="m6 9 6 6 6-6" />
                                    </svg>
                                </span>
                            </div>
                        </div>

                        <div class="bg-white border border-gray-200 rounded-2xl shadow-sm overflow-hidden">
                            <div class="overflow-x-auto">
                                <table class="w-full text-left border-collapse">
                                    <thead>
                                        <tr class="bg-gray-50/50 border-b border-gray-200">
                                            <th
                                                class="px-6 py-5 text-xs font-bold uppercase tracking-wider text-gray-500">
                                                Lecture Detail</th>
                                            <th
                                                class="px-6 py-5 text-xs font-bold uppercase tracking-wider text-gray-500">
                                                Instructor</th>
                                            <th
                                                class="px-6 py-5 text-xs font-bold uppercase tracking-wider text-gray-500">
                                                Date & Location</th>
                                            <th
                                                class="px-6 py-5 text-xs font-bold uppercase tracking-wider text-gray-500">
                                                Status</th>
                                            <th
                                                class="px-6 py-5 text-xs font-bold uppercase tracking-wider text-gray-500 text-right">
                                                Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody id="lectureTableBody" class="divide-y divide-gray-100">
                                        <c:forEach var="lecture" items="${lectures}">
                                            <tr class="hover:bg-blue-50/30 transition-colors group">
                                                <td class="px-6 py-5">
                                                    <div
                                                        class="font-bold text-gray-900 group-hover:text-blue-700 transition-colors">
                                                        ${lecture.title}</div>
                                                    <div class="text-sm text-gray-500 line-clamp-1 max-w-xs">
                                                        ${lecture.description}</div>
                                                </td>
                                                <td class="px-6 py-5">
                                                    <div class="flex items-center gap-2">
                                                        <div
                                                            class="w-8 h-8 rounded-full bg-gray-100 flex items-center justify-center text-xs font-bold text-gray-600">
                                                            ${not empty lecture.instructor ?
                                                            lecture.instructor.substring(0,1) : '?'}
                                                        </div>
                                                        <span class="text-sm font-medium text-gray-700">${not empty
                                                            lecture.instructor ? lecture.instructor :
                                                            'Unassigned'}</span>
                                                    </div>
                                                </td>
                                                <td class="px-6 py-5 text-sm text-gray-600">
                                                    <div class="font-medium">${lecture.lectureDate}</div>
                                                    <div class="text-xs text-gray-400 flex items-center gap-1 mt-0.5">
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12"
                                                            viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                                            stroke-width="2" stroke-linecap="round"
                                                            stroke-linejoin="round">
                                                            <path d="M20 10c0 6-8 12-8 12s-8-6-8-12a8 8 0 0 1 16 0Z" />
                                                            <circle cx="12" cy="10" r="3" />
                                                        </svg>
                                                        ${lecture.location}
                                                    </div>
                                                </td>
                                                <td class="px-6 py-5">
                                                    <c:choose>
                                                        <c:when test="${lecture.status == 'APPROVED'}">
                                                            <span
                                                                class="inline-flex items-center px-2.5 py-1 rounded-lg text-xs font-bold bg-green-50 text-green-700 border border-green-100">
                                                                ● APPROVED
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span
                                                                class="inline-flex items-center px-2.5 py-1 rounded-lg text-xs font-bold bg-amber-50 text-amber-700 border border-amber-100">
                                                                ● PENDING
                                                            </span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="px-6 py-5 text-right">
                                                    <div class="flex justify-end gap-1">
                                                        <c:if test="${lecture.status == 'PENDING'}">
                                                            <form action="admin" method="post" class="inline">
                                                                <input type="hidden" name="action"
                                                                    value="approveLecture">
                                                                <input type="hidden" name="lectureId"
                                                                    value="${lecture.lectureId}">
                                                                <button type="submit"
                                                                    class="p-2 text-gray-400 hover:text-green-600 hover:bg-green-50 rounded-lg transition-all"
                                                                    title="Approve">
                                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18"
                                                                        height="18" viewBox="0 0 24 24" fill="none"
                                                                        stroke="currentColor" stroke-width="2"
                                                                        stroke-linecap="round" stroke-linejoin="round">
                                                                        <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14" />
                                                                        <polyline points="22 4 12 14.01 9 11.01" />
                                                                    </svg>
                                                                </button>
                                                            </form>
                                                        </c:if>

                                                        <a href="admin?action=editLecture&id=${lecture.lectureId}"
                                                            class="p-2 text-gray-400 hover:text-blue-600 hover:bg-blue-50 rounded-lg transition-all"
                                                            title="Edit">
                                                            <svg xmlns="http://www.w3.org/2000/svg" width="18"
                                                                height="18" viewBox="0 0 24 24" fill="none"
                                                                stroke="currentColor" stroke-width="2"
                                                                stroke-linecap="round" stroke-linejoin="round">
                                                                <path
                                                                    d="M17 3a2.85 2.83 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5Z" />
                                                                <path d="m15 5 4 4" />
                                                            </svg>
                                                        </a>
                                                        <form action="admin" method="post"
                                                            onsubmit="return confirm('Delete this lecture?');"
                                                            class="inline">
                                                            <input type="hidden" name="action" value="deleteLecture">
                                                            <input type="hidden" name="id" value="${lecture.lectureId}">
                                                            <button type="submit"
                                                                class="p-2 text-gray-400 hover:text-red-600 hover:bg-red-50 rounded-lg transition-all"
                                                                title="Delete">
                                                                <svg xmlns="http://www.w3.org/2000/svg" width="18"
                                                                    height="18" viewBox="0 0 24 24" fill="none"
                                                                    stroke="currentColor" stroke-width="2"
                                                                    stroke-linecap="round" stroke-linejoin="round">
                                                                    <path d="M3 6h18" />
                                                                    <path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6" />
                                                                    <path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2" />
                                                                    <line x1="10" x2="10" y1="11" y2="17" />
                                                                    <line x1="14" x2="14" y1="11" y2="17" />
                                                                </svg>
                                                            </button>
                                                        </form>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                        <tr id="noResultsRow" style="display: none;">
                                            <td colspan="5" class="px-6 py-20 text-center text-gray-500">
                                                No lectures found matching those criteria.
                                            </td>
                                        </tr>

                                        <c:if test="${empty lectures}">
                                            <tr>
                                                <td colspan="5" class="px-6 py-20 text-center">
                                                    <div class="flex flex-col items-center">
                                                        <p class="text-gray-500 font-medium">No lectures have been
                                                            created yet.</p>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </main>
                </div>

                <script>
                    const searchInput = document.getElementById('searchInput');
                    const statusFilter = document.getElementById('statusFilter');
                    const tableBody = document.getElementById('lectureTableBody');
                    const noResultsRow = document.getElementById('noResultsRow');
                    const rows = tableBody.getElementsByTagName('tr');

                    function filterTable() {
                        const searchText = searchInput.value.toLowerCase();
                        const selectedStatus = statusFilter.value.toUpperCase();
                        let visibleCount = 0;

                        // Loop through rows (skip the noResultsRow and empty lectures placeholders)
                        for (let i = 0; i < rows.length; i++) {
                            const row = rows[i];
                            if (row.id === 'noResultsRow' || row.cells.length < 5) continue;

                            const title = row.cells[0].textContent.toLowerCase();
                            const instructor = row.cells[1].textContent.toLowerCase();
                            const status = row.cells[3].textContent.toUpperCase();

                            const matchesSearch = title.includes(searchText) || instructor.includes(searchText);
                            const matchesStatus = selectedStatus === "" || status.includes(selectedStatus);

                            if (matchesSearch && matchesStatus) {
                                row.style.display = "";
                                visibleCount++;
                            } else {
                                row.style.display = "none";
                            }
                        }

                        // Show 'no results' message if everything is hidden
                        noResultsRow.style.display = (visibleCount === 0 && rows.length > 2) ? "" : "none";
                    }

                    searchInput.addEventListener('keyup', filterTable);
                    statusFilter.addEventListener('change', filterTable);
                </script>

            </body>

            </html>