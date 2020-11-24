package by.gto.test.jsonb.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;

public class Envelope<T> {
    @JsonbTransient
    private final Type actualTypeArgument;
    @JsonbTransient
    private T document;
    @JsonbProperty("data")
    private String jsonData;
    @JsonbProperty("sign")
    private String sign;

    private static final Jsonb jsonb = JsonbBuilder.create();

//    static {
//        JsonbConfig config = new JsonbConfig()
//                .withSerializers(new JsonbGuidSerializer())
//                .withDeserializers(new JsonbGuidDeserializer());
//        jsonb  = JsonbBuilder.create(config);
//    }

    public Envelope() {
        final Class<?> actualClass = this.getClass();
        final Type parent = actualClass.getGenericSuperclass();
        ParameterizedType genericSuperclass = (ParameterizedType) parent;
        actualTypeArgument = genericSuperclass.getActualTypeArguments()[0];
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

    public void setJsonData(String jsonData) {
        this.jsonData = new String(Base64.getDecoder().decode(jsonData), StandardCharsets.UTF_8);
        document = jsonb.fromJson(this.jsonData, actualTypeArgument);
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
