package by.gto.test.jsonb.model;

import by.gto.test.jsonb.helpers.serializers.JsonbDateSerializer;
import java.util.Date;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeSerializer;

public class Test {
//    @JsonbTypeSerializer(JsonbDateSerializer.class)
    @JsonbProperty("d")
    public Date date = new Date();
}
