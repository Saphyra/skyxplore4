package com.github.saphyra.skyxplore.game.player.data;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AiNameConfiguration {
    @Bean
    public FirstNames firstNames(
        ApplicationContext applicationContext,
        ObjectMapper objectMapperWrapper
    ) throws IOException {
        Resource resource = applicationContext.getResource("classpath:public/data/first_name.json");
        FirstNames firstNames = objectMapperWrapper.readValue(resource.getInputStream(), FirstNames.class);
        log.info("FirstNames loaded: {}", firstNames);
        return firstNames;
    }

    @Bean
    public LastNames lastNames(
        ApplicationContext applicationContext,
        ObjectMapper objectMapperWrapper
    ) throws IOException {
        Resource resource = applicationContext.getResource("classpath:public/data/last_name.json");
        LastNames lastNames = objectMapperWrapper.readValue(resource.getInputStream(), LastNames.class);
        log.info("LastNames loaded: {}", lastNames);
        return lastNames;
    }
}
