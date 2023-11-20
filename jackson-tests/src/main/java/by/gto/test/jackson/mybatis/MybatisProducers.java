package by.gto.test.jackson.mybatis;

import java.io.IOException;
import java.io.Reader;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.cdi.SessionFactoryProvider;

@ApplicationScoped
public class MybatisProducers {
    @ApplicationScoped
    @Produces
    @SessionFactoryProvider
    @Default
    @Named("globalMyBatisSessionFactory")
    public SqlSessionFactory produceMaster() {
        try (final Reader configResource = Resources.getResourceAsReader("mybatis-config.xml")) {
            return new SqlSessionFactoryBuilder().build(configResource, "master");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
