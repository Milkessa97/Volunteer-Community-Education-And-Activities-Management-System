<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Community Service - Login</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/lucide/0.263.0/lucide.min.css" rel="stylesheet">
</head>
<body class="min-h-screen bg-gradient-to-br from-slate-50 via-blue-50/20 to-slate-50 flex items-center justify-center p-4">

    <div class="bg-white rounded-2xl border border-slate-200 shadow-xl w-full max-w-md p-8">

        <div class="text-center mb-8">
            <div class="mx-auto w-16 h-16 bg-blue-600 rounded-2xl flex items-center justify-center mb-4 shadow-lg shadow-blue-200">
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 7v14"/><path d="M3 18a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h5a4 4 0 0 1 4 4 4 4 0 0 1 4-4h5a1 1 0 0 1 1 1v13a1 1 0 0 1-1 1h-6a3 3 0 0 0-3 3 3 3 0 0 0-3-3z"/></svg>
            </div>
            <h1 class="text-3xl font-bold text-slate-900">Community Service</h1>
            <p class="text-slate-500 mt-2">Volunteer Lecture Management System</p>
        </div>

        <% if (request.getParameter("error") != null) { %>
            <div class="mb-4 p-3 rounded-lg bg-red-50 border border-red-100 text-red-600 text-sm">
                Invalid credentials or session expired.
            </div>
        <% } %>

        <form action="login" method="POST" class="space-y-5">
            <input type="hidden" name="role" id="selectedRole" value="">

            <div>
                <label class="block text-sm font-semibold text-slate-700 mb-1">Username</label>
                <input type="text" name="username" placeholder="yourname" class="w-full px-4 py-3 rounded-lg border border-slate-200 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all" required>
            </div>

            <div>
                <label class="block text-sm font-semibold text-slate-700 mb-1">Password</label>
                <input type="password" name="password" placeholder="Enter password" class="w-full px-4 py-3 rounded-lg border border-slate-200 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all" required>
            </div>

            <button type="submit" class="w-full py-3 bg-blue-600 hover:bg-blue-700 text-white font-bold rounded-xl shadow-lg shadow-blue-200 transition-all active:scale-[0.98]">
                Sign In
            </button>
        </form>
    </div>

    <script>
        function selectRole(role, element) {
            // Remove active classes from all buttons
            document.querySelectorAll('.role-btn').forEach(btn => {
                btn.classList.remove('border-blue-600', 'bg-blue-50');
                btn.classList.add('border-slate-100');
            });
            // Add active class to selected button
            element.classList.remove('border-slate-100');
            element.classList.add('border-blue-600', 'bg-blue-50');
            // Update hidden input
            document.getElementById('selectedRole').value = role;
        }
    </script>
</body>
</html>