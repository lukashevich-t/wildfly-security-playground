package by.gto.test.jackson.helpers;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@Provider
public class LogFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext reqContext) throws IOException {
        System.err.println("-- req info --");
        log(reqContext.getUriInfo(), reqContext.getHeaders());
    }

    private void log(UriInfo uriInfo, MultivaluedMap<String, ?> headers) {
        System.out.println("Path: " + uriInfo.getPath());
        headers.entrySet().forEach(h -> System.err.println(h.getKey() + ": " + h.getValue()));
    }
}
