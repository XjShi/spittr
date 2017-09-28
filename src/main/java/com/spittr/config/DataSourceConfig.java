package com.spittr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class DataSourceConfig {
    @Autowired
    org.springframework.core.env.Environment env;

    @Bean
    @Profile("product")
    public DataSource dataSource() {
        DataSource dataSource = mysqlDataSource(env.getProperty("database.url"),
                env.getProperty("database.username"),
                env.getProperty("database.password"));
        return dataSource;
    }

    @Bean(name = "devEnvDataSource")
    @Profile("qa")
    public DataSource devEnvDataSource() {
        DataSource dataSource = mysqlDataSource(env.getProperty("database.url.dev"),
                env.getProperty("database.username"),
                env.getProperty("database.password"));

        return dataSource;
    }

    private DataSource mysqlDataSource(String url, String username, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("database.driver"));
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "dataSource")
    @Profile("dev")
    public DataSource embeddedDatasource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("sql/test-schema.sql")
                .build();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}