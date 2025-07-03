package plaza.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    //@Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }

    @Bean
    public DataSource hikariDataSourse(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driver);
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(url);

        config.setMaximumPoolSize(10); //maximo de conexoes liberadas
        config.setMinimumIdle(1); //tamanho inicial do pool
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); //600k ms (10 min)
        config.setConnectionTimeout(100000); //timeout para conseguir uma conexao
        config.setConnectionInitSql("select 1"); //query de test

        return new HikariDataSource(config);
    }
}
