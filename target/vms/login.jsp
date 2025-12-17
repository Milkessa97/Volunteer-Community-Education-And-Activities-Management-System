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

        <div class="mb-6">
            <label class="block text-sm font-semibold text-slate-700 mb-3">Select Role</label>
            <div class="grid grid-cols-3 gap-3">
                <button type="button" onclick="selectRole('STUDENT', this)" class="role-btn p-4 rounded-xl border-2 border-slate-100 hover:border-blue-200 hover:bg-blue-50/50 transition-all text-center">
                    <svg class="mx-auto mb-2 text-slate-400" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M22 10v6M2 10l10-5 10 5-10 5z"/><path d="M6 12.5V16a6 3 0 0 0 12 0v-3.5"/></svg>
                    <span class="text-xs font-bold text-slate-600">Student</span>
                </button>
                <button type="button" onclick="selectRole('VOLUNTEER', this)" class="role-btn p-4 rounded-xl border-2 border-slate-100 hover:border-blue-200 hover:bg-blue-50/50 transition-all text-center">
                    <svg class="mx-auto mb-2 text-slate-400" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3 .5-4.5 2-1.5-1.5-2.74-2-4.5-2A5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z"/></svg>
                    <span class="text-xs font-bold text-slate-600">Volunteer</span>
                </button>
                <button type="button" onclick="selectRole('ADMIN', this)" class="role-btn p-4 rounded-xl border-2 border-slate-100 hover:border-blue-200 hover:bg-blue-50/50 transition-all text-center">
                    <svg class="mx-auto mb-2 text-slate-400" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/></svg>
                    <span class="text-xs font-bold text-slate-600">Admin</span>
                </button>
            </div>
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

        <p class="text-center text-xs text-slate-400 mt-6 uppercase tracking-widest">Demo Prototype - VMS 2025</p>
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