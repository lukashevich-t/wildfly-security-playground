package by.gto.test.jsonb.helpers.serializers;

import java.lang.reflect.Type;
import java.util.UUID;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

public class JsonbGuidSerializer implements JsonbSerializer<byte[]> {
    @Override
    public void serialize(byte[] value, JsonGenerator generator, SerializationContext ctx) {
        if (value != null) {
            String formattedGuid = guidFromBytes(value).toString();
            ctx.serialize(/* obj.getClass().getName(), */ formattedGuid, generator);
        } else {
            ctx.serialize(null, generator);
        }
    }

    public static UUID guidFromBytes(byte[] bytes) {
        long mostSigBits
                = (((long) bytes[0] << 32) & 0x000000ff00000000L)
                | (((long) bytes[1] << 40) & 0x0000ff0000000000L)
                | (((long) bytes[2] << 48) & 0x00ff000000000000L)
                | (((long) bytes[3] << 56) & 0xff00000000000000L)
                | (((long) bytes[4] << 16) & 0x0000000000ff0000L)
                | (((long) bytes[5] << 24) & 0x00000000ff000000L)
                | (((long) bytes[6]) & 0x00000000000000ffL)
                | (((long) bytes[7] << 8) & 0x000000000000ff00L);

        long leastSigBits
                = (((long) bytes[8] << 56) & 0xff00000000000000L)
                | (((long) bytes[9] << 48) & 0x00ff000000000000L)
                | (((long) bytes[10] << 40) & 0x0000ff0000000000L)
                | (((long) bytes[11] << 32) & 0x000000ff00000000L)
                | (((long) bytes[12] << 24) & 0x00000000ff000000L)
                | (((long) bytes[13] << 16) & 0x0000000000ff0000L)
                | (((long) bytes[14] << 8) & 0x000000000000ff00L)
                | (((long) bytes[15]) & 0x00000000000000ffL);

        return new UUID(mostSigBits, leastSigBits);
    }
}
