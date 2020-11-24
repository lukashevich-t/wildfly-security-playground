package by.gto.test.jackson.mybatis;

import by.gto.test.jackson.model.Og;
import org.mybatis.cdi.Mapper;

@Mapper
public interface TestMappers {
    int saveOg(Og og);
}
