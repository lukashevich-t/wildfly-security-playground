package by.gto.test.jackson.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;


/**
 * Класс, регистрирующий преобразователи в/из значений даты для классов Date, LocalDateTime, LocalDate.
 */
@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {
    private final ObjectMapper objectMapper;

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }

    public JacksonConfig() {
        objectMapper = by.gto.test.helpers.JacksonConfig.getDefaultObjectMapper();
    }
}
