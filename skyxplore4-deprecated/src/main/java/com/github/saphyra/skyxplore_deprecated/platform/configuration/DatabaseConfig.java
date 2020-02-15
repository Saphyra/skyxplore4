package com.github.saphyra.skyxplore_deprecated.platform.configuration;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DatabaseConfig {
    @Bean
    public SpringLiquibase liquibase(
        DataSource dataSource,
        @Value("${liquibase.changelog.location}") String changeLogLocation
    ) {
        log.debug("ChangeLogLocation: {}", changeLogLocation);
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(changeLogLocation);
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}
