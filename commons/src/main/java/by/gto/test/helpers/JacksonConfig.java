package by.gto.test.helpers;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class JacksonConfig {
    private static ObjectMapper objectMapper = null;
    public static ObjectMapper getDefaultObjectMapper() {
        if(objectMapper == null) {
            final SimpleModule m = new SimpleModule("MyModule", new Version(1, 0, 0, null, "", ""));
            m.addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
            m.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
            m.addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
            m.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
            m.addSerializer(Date.class, DateSerializer.instance);
            m.addDeserializer(Date.class, DateDeserializers.DateDeserializer.instance);
            ObjectMapper om = new ObjectMapper();
            om.registerModule(m);
            om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper = om;
        }
        return objectMapper;
    }
}
