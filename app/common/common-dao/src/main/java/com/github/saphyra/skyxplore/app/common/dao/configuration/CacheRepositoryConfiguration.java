package com.github.saphyra.skyxplore.app.common.dao.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;

@ConditionalOnProperty(value = "com.github.saphyra.skyxplore.cacheRepository.enabled", havingValue = "true")
@Configuration
@ComponentScan(basePackageClasses = CacheRepository.class)
public class CacheRepositoryConfiguration {
}
