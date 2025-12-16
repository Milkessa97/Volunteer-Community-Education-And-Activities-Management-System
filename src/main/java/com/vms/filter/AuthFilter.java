package com.vms.filter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
@WebFilter("/*")
public class AuthFilter implements Filter {
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
throws IOException, ServletException {
HttpServletRequest request = (HttpServletRequest) req;
HttpSession session = request.getSession(false);


String path = request.getRequestURI();
if (path.endsWith("/login")) {
chain.doFilter(req, res);
return;
}


if (session == null || session.getAttribute("user") == null) {
((HttpServletResponse) res).sendError(401);
return;
}
chain.doFilter(req, res);
}
}