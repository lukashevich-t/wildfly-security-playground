package by.gto.test.jackson.helpers;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

@WebFilter(urlPatterns = {"/*"}, initParams = {@WebInitParam(name = "a", value = "b")})
//urlPatterns = ["/s/*"], initParams = [WebInitParam(name = "1234", value = "1234")])
public class PassFilter implements Filter {
    private final PrintStream outStream = System.err;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        outStream.println("Passfilter init");
        outStream.println(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest rq = (HttpServletRequest) request;
        outStream.println(rq);
        rq.getHttpServletMapping().getMatchValue().startsWith("login");
        String authType = rq.getAuthType();
        outStream.println(authType);
        if (null == authType) {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            resp.getOutputStream().write("{errCode: 123, message: '12334234'}".getBytes(StandardCharsets.UTF_8));
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        outStream.println("PassFilter destroy");
    }
}
