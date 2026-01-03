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
           
        </body>

        </html>