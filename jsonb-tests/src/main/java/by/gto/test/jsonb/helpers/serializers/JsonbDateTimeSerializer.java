package by.gto.test.jsonb.helpers.serializers;

import by.gto.test.helpers.PooledSimpleDateFormat;
import by.gto.test.helpers.pools.Pools;
import java.util.Date;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

public class JsonbDateTimeSerializer implements JsonbSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator generator, SerializationContext ctx) {
        try (PooledSimpleDateFormat sdf = Pools.formatDateAndTime4Json.borrowObject()) {
            ctx.serialize(sdf.format(date), generator);
        }
    }
}
