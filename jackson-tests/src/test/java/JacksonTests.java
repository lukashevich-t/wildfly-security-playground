import by.gto.test.jackson.model.Envelope;
import by.gto.test.jackson.model.Og;
import by.gto.test.jackson.model.OgEnvelope;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.junit.Test;

public class JacksonTests {
    @Test
    public void reflection() {
        final OgEnvelope ogEnvelope = new OgEnvelope();

        final Class<?> actualClass = ogEnvelope.getClass();
        final ParameterizedType genericSuperclass = (ParameterizedType) (actualClass.getGenericSuperclass());
        final Type actualTypeArgument = genericSuperclass.getActualTypeArguments()[0];
        final Class<?> actualTypeClass = (Class<?>) actualTypeArgument;
    }

    @Test
    public void reflection2() throws IOException {
        final OgEnvelope ogEnvelope = new OgEnvelope();
        System.out.println(ogEnvelope);
        ogEnvelope.setJsonData("ewogICAgIngiOiAxLAogICAgImRhdGVPbmx5IjogIjIwMjAtMTAtMTAiLAogICAgImRhdGVXaXRoVGltZSI6ICIyMDIwLTEwLTEwVDEwOjIwOjQwIiwKICAgICJsb2NhbERhdGUiOiAiMjAyMC0xMC0xMCIsCiAgICAibG9jYWxEYXRlVGltZSI6ICIyMDIwLTEwLTEwVDEwOjIwOjQwIiwKICAgICJtZXNzYWdlIjogItCvINC30LTQtdGB0YwhIiwKICAgICJndWlkIjogImNmNjVhYzY3LTliMmEtNDQzNy1hZDc3LWExODNmYzc4YzEzMCIKfQ==");
    }

}