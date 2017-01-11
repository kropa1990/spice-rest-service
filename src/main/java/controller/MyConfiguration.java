package controller;

import dao.SpiceDAO;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.activation.DataSource;

/**
 * Created by lkropiew on 2017-01-11.
 */
@Configuration
public class MyConfiguration {

    @Bean
    public SpiceDAO spiceDAO() {
        return new SpiceDAO();
    }

}
