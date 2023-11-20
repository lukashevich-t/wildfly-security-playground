package by.gto.test.jsonb.model;

import by.gto.test.jsonb.helpers.deserializers.JsonbDateDeserializer;
import by.gto.test.jsonb.helpers.deserializers.JsonbDateTimeDeserializer;
import by.gto.test.jsonb.helpers.deserializers.JsonbGuidDeserializer;
import by.gto.test.jsonb.helpers.serializers.JsonbDateSerializer;
import by.gto.test.jsonb.helpers.serializers.JsonbDateTimeSerializer;
import by.gto.test.jsonb.helpers.serializers.JsonbGuidSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import javax.json.bind.annotation.JsonbNillable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeDeserializer;
import javax.json.bind.annotation.JsonbTypeSerializer;

@JsonbNillable(false)
public class Og {
    @JsonbTypeDeserializer(JsonbGuidDeserializer.class)
    @JsonbTypeSerializer(JsonbGuidSerializer.class)
    public byte[] guid;

    public int x;

    @JsonbTypeDeserializer(JsonbDateDeserializer.class)
    @JsonbTypeSerializer(JsonbDateSerializer.class)
    public Date dateOnly;

//    @JsonbTypeDeserializer(JsonbDateTimeDeserializer.class)
//    @JsonbTypeSerializer(JsonbDateTimeSerializer.class)
    public Date dateWithTime;

    public LocalDate localDate;

    public LocalDateTime localDateTime;

    public String message;

    @Override
    public String toString() {
        return "Og{" +
                "guid=" + Arrays.toString(guid) +
                ", x=" + x +
                ", dateOnly=" + dateOnly +
                ", dateWithTime=" + dateWithTime +
                ", localDate=" + localDate +
                ", localDateTime=" + localDateTime +
                ", message='" + message + '\'' +
                '}';
    }
}
