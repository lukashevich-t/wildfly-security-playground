//package by.gto.test.jackson.model;
//
//import com.fasterxml.jackson.databind.util.StdConverter;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//
//public class EnvelopeUnpacker<T> extends StdConverter<Envelope<T>, Envelope<T>> {
//    @Override
//    public Envelope<T> convert(Envelope<T> value) {
//        final Class<?> actualClass = value.getClass();
//        final ParameterizedType genericSuperclass = (ParameterizedType) (actualClass.getGenericSuperclass());
//        final Type actualTypeArgument = genericSuperclass.getActualTypeArguments()[0];
//        Class<?> actualTypeClass = (Class<?>) actualTypeArgument;
//
//        return value;
//    }
//}
