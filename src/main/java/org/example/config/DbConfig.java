package org.example.config;

import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DbConfig {

    @Bean
    public static DataSource dataSource() {
        var dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://postgresql:5432/map");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplateMap() {
        return new JdbcTemplate(dataSource());
    }
}
