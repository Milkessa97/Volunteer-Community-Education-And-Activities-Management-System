<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Community Service</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        /* Custom styles to match your provided UI */
        :root {
            --primary: 221.2 83.2% 53.3%;
            --background: 210 40% 98%;
            --card: 0 0% 100%;
        }
    </style>
</head>
<body class="min-h-screen bg-slate-50">

    <div id="root">
        <header class="border-b border-slate-200 bg-white sticky top-0 z-50">
            <div class="container mx-auto px-4 py-4 flex items-center justify-between">
                <div class="flex items-center gap-2">
                    <h1 class="text-xl font-semibold text-slate-900">Community Service</h1>
                </div>
                <div class="flex items-center gap-4">
                    <div class="text-right hidden sm:block">
                        <p class="text-sm font-medium text-slate-900">
                            <c:out value="${sessionScope.user.name}" default="Admin User"/>
                        </p>
                        <p class="text-xs text-slate-500 capitalize">admin</p>
                    </div>
                    <form action="logout" method="post">
                        <button type="submit" class="inline-flex items-center justify-center gap-2 text-sm font-medium border border-slate-200 bg-white hover:bg-slate-50 h-9 rounded-md px-3">
                            Logout
                        </button>
                    </form>
                </div>
            </div>
        </header>

        <main class="container mx-auto px-4 py-8">
            <div class="mb-8 flex items-center justify-between">
                <div>
                    <h1 class="text-4xl font-bold text-slate-900 mb-2">Admin Dashboard</h1>
                    <p class="text-slate-500 text-lg">System overview and management</p>
                </div>
                <a href="create-lecture.jsp" class="inline-flex items-center justify-center bg-blue-600 text-white hover:bg-blue-700 h-11 rounded-md px-8">
                    Create Lecture
                </a>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
                <div class="rounded-lg border bg-white p-6 shadow-sm hover:shadow-md transition-shadow">
                    <p class="text-sm text-slate-500 font-medium">Upcoming Sessions</p>
                    <p class="text-3xl font-bold text-slate-900">${upcomingCount}</p>
                </div>
                </div>

            <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
                <div class="bg-white border border-slate-200 rounded-xl p-6 shadow-sm">
                    <h3 class="text-xl font-semibold text-slate-900 mb-2">Manage Lectures</h3>
                    <p class="text-sm text-slate-500 mb-4">View, edit, or delete lectures and track attendance</p>
                    <a href="lectures" class="inline-flex items-center justify-center w-full bg-blue-600 text-white h-11 rounded-md px-8">
                        View All Lectures
                    </a>
                </div>

                <div class="bg-gradient-to-br from-blue-50 to-indigo-50 border border-slate-200 rounded-xl p-6">
                    <h3 class="text-lg font-semibold text-slate-900 mb-3">🏆 Most Popular Lecture</h3>
                    <p class="font-medium text-slate-900">${popularLecture.title}</p>
                    <p class="text-sm text-slate-500">by ${popularLecture.instructor}</p>
                </div>
            </div>
        </main>
    </div>

</body>
</html>