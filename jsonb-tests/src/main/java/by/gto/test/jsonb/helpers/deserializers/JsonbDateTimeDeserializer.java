package by.gto.test.jsonb.helpers.deserializers;

import by.gto.test.helpers.PooledSimpleDateFormat;
import by.gto.test.helpers.pools.Pools;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;

public class JsonbDateTimeDeserializer implements JsonbDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser parser, DeserializationContext ctx, Type rtType) {
        try (PooledSimpleDateFormat sdf = Pools.formatDateAndTime4Json.borrowObject()) {
            return sdf.parse(parser.getString());
        } catch (ParseException e) {
            return null;
        }
    }
}
