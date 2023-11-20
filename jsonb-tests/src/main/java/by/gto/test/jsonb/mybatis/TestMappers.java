package by.gto.test.jsonb.mybatis;

import by.gto.test.jsonb.model.Og;
import org.apache.ibatis.annotations.Param;
import org.mybatis.cdi.Mapper;

@Mapper
public interface TestMappers {
    int saveOg(Og og);
}
