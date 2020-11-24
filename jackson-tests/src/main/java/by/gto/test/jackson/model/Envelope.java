package by.gto.test.jackson.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Envelope<T> {

    @JsonIgnore
    private final Class<?> actualTypeClass;

    @JsonIgnore
    private T document;
    @JsonProperty("data")
    private String jsonData;
    @JsonProperty("sign")
    private String sign;

    private static final ObjectMapper om = by.gto.test.helpers.JacksonConfig.getDefaultObjectMapper();

    public Envelope() {
        final Class<?> actualClass = this.getClass();
        final ParameterizedType genericSuperclass = (ParameterizedType) (actualClass.getGenericSuperclass());
        final Type actualTypeArgument = genericSuperclass.getActualTypeArguments()[0];
        actualTypeClass = (Class<?>) actualTypeArgument;
    }

    //<editor-fold desc="getters">
    public T getDocument() {
        return document;
    }

    public void setDocument(T document) {
        this.document = document;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) throws IOException {
        this.jsonData = new String(Base64.getDecoder().decode(jsonData), StandardCharsets.UTF_8);
        final ObjectReader or = om.readerFor(actualTypeClass);
        document = or.readValue(this.jsonData);
    }

    @Override
    public String toString() {
        return "Envelope{" +
                "document=" + document +
                ", jsonData='" + jsonData + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
    //</editor-fold>
}
