package by.gto.test.jackson.helpers;

import by.gto.test.jackson.AisException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        Throwable cause = exception;
        int code = 400;
        String message = exception.getMessage();
        List<Throwable> seenList = new ArrayList<>();
        while (cause != null) {
            // Избежим циклических ссылок
            for (Throwable t : seenList) {
                if (t == cause) {
                    break;
                }
            }
            if (cause instanceof AisException) {
                message = cause.getMessage();
                code = ((AisException) cause).getHttpCode();
                break;
            }
            seenList.add(cause);
            cause = cause.getCause();
        }
        return Response.status(Response.Status.fromStatusCode(code)).entity(message).build();
    }
}
