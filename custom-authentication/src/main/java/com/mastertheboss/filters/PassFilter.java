//package com.mastertheboss.filters;
//
//
//import java.io.IOException;
//import java.io.PrintStream;
//import java.nio.charset.StandardCharsets;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.annotation.WebInitParam;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.ws.rs.core.Response;
//
//@WebFilter(urlPatterns = {"/*"}, initParams = {@WebInitParam(name = "a", value = "b")})
////urlPatterns = ["/s/*"], initParams = [WebInitParam(name = "1234", value = "1234")])
//public class PassFilter implements Filter {
//    private final PrintStream outStream = System.err;
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        outStream.println("Passfilter init");
//        outStream.println(filterConfig);
//    }
//
//    private void makeUnauthenticatedAnswer(ServletResponse response) throws IOException {
//        HttpServletResponse resp = (HttpServletResponse) response;
//        resp.setHeader("Content-Type", "application/json");
//        resp.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
//        resp.getOutputStream().write("{errCode: 401, message: 'UNAUTHORIZED'}".getBytes(StandardCharsets.UTF_8));
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        final HttpServletRequest rq = (HttpServletRequest) request;
//        outStream.println(rq);
//        if (rq.getHttpServletMapping().getMatchValue().startsWith("o/")) {
//            chain.doFilter(request, response);
//        } else {
//            final HttpSession session = rq.getSession(false);
//            outStream.println(session);
////            String authType = rq.getAuthType();
////            outStream.println(authType);
//            if (null == session || rq.getUserPrincipal() == null) {
//                makeUnauthenticatedAnswer(response);
//            } else {
//                chain.doFilter(request, response);
//            }
//        }
//    }
//
//    @Override
//    public void destroy() {
//        outStream.println("PassFilter destroy");
//    }
//}
