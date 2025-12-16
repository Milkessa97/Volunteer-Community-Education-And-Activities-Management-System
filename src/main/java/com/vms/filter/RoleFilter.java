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
import com.vms.model.User;
@WebFilter("/admin/*")
public class RoleFilter implements Filter {
public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
throws IOException, ServletException {
HttpSession session = ((HttpServletRequest) req).getSession(false);
User user = (User) session.getAttribute("user");


if (!"ADMIN".equals(user.getRole())) {
((HttpServletResponse) res).sendError(403);
return;
}
chain.doFilter(req, res);
}
}
