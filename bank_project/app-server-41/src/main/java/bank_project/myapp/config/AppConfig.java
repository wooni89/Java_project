package bank_project.myapp.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bank_project.util.Bean;
import bank_project.util.ComponentScan;
import bank_project.util.SqlSessionFactoryProxy;

@ComponentScan(basePackages={"bank_project.myapp.dao", "bank_project.myapp.handler"})
public class AppConfig {
  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    return new SqlSessionFactoryProxy(
        new SqlSessionFactoryBuilder().build(
            Resources.getResourceAsStream("bank_project/myapp/config/mybatis-config.xml")));
  }

}
