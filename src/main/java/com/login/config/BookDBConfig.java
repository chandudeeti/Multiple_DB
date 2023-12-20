package com.login.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "bookEntityManagerFactory",
        basePackages = {"com.login.book.repository"},
        transactionManagerRef = "bookTransactionManager"
)
public class BookDBConfig {

    @Bean(name = "bookDatasource")
    @ConfigurationProperties(prefix = "spring.book.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "bookEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            EntityManagerFactoryBuilder factoryBuilder,
            @Qualifier("bookDatasource") DataSource dataSource
    ) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return factoryBuilder
                .dataSource(dataSource)
                .packages("com.login.model.book")
                .persistenceUnit("Book")
                .properties(properties)
                .build();
    }


    @Bean(name = "bookTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("bookEntityManagerFactory") EntityManagerFactory entityManagerFactory )
    {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
