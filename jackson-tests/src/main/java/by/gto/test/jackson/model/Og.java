package by.gto.test.jackson.model;

import by.gto.test.jackson.helpers.deserializers.JsonGuidDeserializer;
import by.gto.test.jackson.helpers.serializers.JsonGuidSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

public class Og {
    @JsonSerialize(using = JsonGuidSerializer.class)
    @JsonDeserialize(using = JsonGuidDeserializer.class)
    public byte[] guid;

    public int x;

    public Date dateOnly;

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
