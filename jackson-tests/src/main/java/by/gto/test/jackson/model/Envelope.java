package by.gto.test.jackson.model;

import by.gto.test.jackson.AisException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Envelope {
    /**
     * Данные в формате json
     */
    @JsonProperty("d")
    private final byte[] data;

    /**
     * Сигнатура
     */
    @JsonProperty("s")
    private final byte[] sign;

    private static final ObjectMapper om = by.gto.test.helpers.JacksonConfig.getDefaultObjectMapper();

    @JsonCreator
    public Envelope(@JsonProperty("d") String encodedData, @JsonProperty("s") String encodedSign) throws Exception {
        final Base64.Decoder decoder = Base64.getDecoder();
        final byte[] decodedDataAsBytes = decoder.decode(encodedData);
        this.data = decodedDataAsBytes;
//        this.data = new String(decodedDataAsBytes, StandardCharsets.UTF_8);
        this.sign = decoder.decode(encodedSign);

        // Проверить подпись:
        final MessageDigest md = MessageDigest.getInstance("SHA-256");
        if (!Arrays.equals(this.sign, md.digest(decodedDataAsBytes))) {
            throw new AisException("Ошибка проверки ЭЦП!", -1, 500);
        }
    }

    public <T> T getDocument(Class<T> type) throws IOException {
        final T t = om.readValue(data, type);
        return t;
    }

    public byte[] getData() {
        return data;
    }

    public byte[] getSign() {
        return sign;
    }
}
