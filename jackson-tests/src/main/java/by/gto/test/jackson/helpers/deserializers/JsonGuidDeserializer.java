package by.gto.test.jackson.helpers.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.UUID;

public class JsonGuidDeserializer extends JsonDeserializer<byte[]> {
    @Override
    public byte[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String date = p.getText();
        try {
            return guidAsBytes(UUID.fromString(date));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static byte[] guidAsBytes(UUID guid) {
        long longOne = guid.getMostSignificantBits();
        long longTwo = guid.getLeastSignificantBits();

        return new byte[]{
                (byte) (longOne >>> 32), // 0
                (byte) (longOne >>> 40), // 1
                (byte) (longOne >>> 48), // 2
                (byte) (longOne >>> 56), // 3
                (byte) (longOne >>> 16), // 4
                (byte) (longOne >>> 24), // 5
                (byte) longOne, // 6
                (byte) (longOne >>> 8), // 7

                (byte) (longTwo >>> 56), // 8
                (byte) (longTwo >>> 48), // 9
                (byte) (longTwo >>> 40), // 10
                (byte) (longTwo >>> 32), // 11
                (byte) (longTwo >>> 24), // 12
                (byte) (longTwo >>> 16), // 13
                (byte) (longTwo >>> 8), // 14
                (byte) longTwo // 15
        };
    }
}
