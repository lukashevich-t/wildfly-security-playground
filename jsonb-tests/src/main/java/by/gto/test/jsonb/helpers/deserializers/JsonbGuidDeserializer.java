package by.gto.test.jsonb.helpers.deserializers;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;

public class JsonbGuidDeserializer implements JsonbDeserializer<byte[]> {
    @Override
    public byte[] deserialize(JsonParser parser, DeserializationContext ctx, Type rtType) {
        try {
            return guidAsBytes(UUID.fromString(parser.getString()));
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
