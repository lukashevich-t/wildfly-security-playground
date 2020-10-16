package com.mastertheboss.filters;

import by.gto.ais.kotlin.KotlinTest;
import io.undertow.servlet.spec.HttpSessionImpl;
import io.undertow.servlet.spec.ServletContextImpl;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.wildfly.security.auth.server.SecurityDomain;
import org.wildfly.security.auth.server.SecurityIdentity;
import org.wildfly.security.authz.Attributes.Entry;

@WebFilter(urlPatterns = "/rest/*", initParams = { @WebInitParam(name = "1234", value = "1234") })
public class LogFilter implements Filter {

    private String passwordExpiredPage = "passwordExpired.html";

    public LogFilter() {
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        System.out.println("LogFilter.init");
        KotlinTest.S.x();
    }

    @Override
    public void destroy() {
        System.out.println("LogFilter.destroy!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        Enumeration<String> attributeNames = req.getAttributeNames();

        req.getSession().setAttribute("login", req.getRemoteUser());
        req.getSession().setAttribute("remoteHost", req.getRemoteHost());
        while (attributeNames.hasMoreElements()) {
            String attName = attributeNames.nextElement();
            Object attribute = req.getAttribute(attName);
            String className = (attribute == null) ? null : attribute.getClass().getCanonicalName();
            System.out.printf("LogFilter.doFilter attribute %s of class %s: %s%n", attName, className, attribute);
        }

        System.out.println();
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String header = req.getHeader(headerName);
            System.out.printf("LogFilter.doFilter header %s -> %s%n", headerName, header);
        }

        System.out.println("LogFilter.doFilter remote user is " + req.getRemoteUser());
        System.out.println("LogFilter.doFilter remote host is " + req.getRemoteHost());
        System.out.println("LogFilter.doFilter remote addr is " + req.getRemoteAddr());
        System.out.println("LogFilter.doFilter remote port is " + req.getRemotePort());

        describePrincipal(req.getUserPrincipal());
        System.out.println("LogFilter.doFilter getRequestedSessionId: " + req.getRequestedSessionId());

        HttpSession session = req.getSession(false);
        describeSession(session);
        describeServletContext(session.getServletContext());

        SecurityIdentity securityIdentity = SecurityDomain.getCurrent().getCurrentSecurityIdentity();
        System.out.println("LogFilter.doFilter Security identity is " + securityIdentity);

        Collection<Entry> entries = securityIdentity.getAttributes().entries();

        boolean passwordExpired = false;
        String passwordExpirationDate = "";
        for (Entry entry : entries) {
            System.out.printf("LogFilter.doFilter security attribute %s -> %s%n", entry.getKey(), entry);
            if ("passwordExpired".equals(entry.getKey()) && entry.size() > 0) {
                String temp = entry.get(0);
                passwordExpired = !("0".equals(temp) || "false".equals(temp));
            }
            if ("passwordExpirationDate".equals(entry.getKey()) && entry.size() > 0) {
                passwordExpirationDate = entry.get(0);
            }
        }

        if (passwordExpired) {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendRedirect(
                    req.getContextPath() + "/" + this.passwordExpiredPage + "?date=" + passwordExpirationDate);
        }
//		String servletPath = req.getServletPath();
//
//		System.out.println("#INFO " + new Date() + " - ServletPath :" + servletPath //
//				+ ", URL =" + req.getRequestURL());

        // Разрешить request продвигаться дальше. (Перейти данный Filter).
        chain.doFilter(request, response);
    }

    private void describeServletContext(ServletContext sc) {
        System.out.printf("ServletContext of class %s is %s%n", sc.getClass().getCanonicalName(), sc);
        if (sc instanceof ServletContextImpl) {
            ServletContextImpl wfSc = (ServletContextImpl) sc;
        }

        Enumeration<String> attributeNames = sc.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            System.out.printf("ServletContext attribute %s -> %s%n", name, sc.getAttribute(name));
        }

        System.out.println("Session timeout " + sc.getSessionTimeout());
        System.out.println("getRequestCharacterEncoding " + sc.getRequestCharacterEncoding());
        System.out.println("getResponseCharacterEncoding " + sc.getResponseCharacterEncoding());

        System.out.println("\n\n");
    }

    private void describeSession(HttpSession httpSession) {
        System.out.println("HttpSession: " + httpSession);
        if (httpSession instanceof HttpSessionImpl) {
            HttpSessionImpl wfSession = (HttpSessionImpl) httpSession;
        }

        Enumeration<String> attributeNames = httpSession.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            String attName = attributeNames.nextElement();
            Object attribute = httpSession.getAttribute(attName);
            String className = (attribute == null) ? null : attribute.getClass().getCanonicalName();
            System.out.printf("attribute %s of class %s: %s%n", attName, className, attribute);
        }
        System.out.println("\n\n");
    }

    private void describePrincipal(Principal principal) {
        if (principal == null) {
            System.out.println("Principal is null");
        } else {
            System.out.printf("Principal %s of class %s is %s%n", principal, principal.getClass().getCanonicalName(),
                    principal);
        }
    }
}
