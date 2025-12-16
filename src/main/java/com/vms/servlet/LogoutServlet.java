package com.vms.servlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
throws IOException {
req.getSession().invalidate();
resp.getWriter().write("Logged out");
}
}