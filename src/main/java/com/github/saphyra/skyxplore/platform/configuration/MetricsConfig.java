package com.github.saphyra.skyxplore.platform.configuration;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.saphyra.skyxplore.platform.MetricsFilter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@ConditionalOnProperty(value = "metrics.enabled", havingValue = "true")
@Slf4j
public class MetricsConfig {
    @Bean
    public MetricsFilter metricsFilter(){
        return new MetricsFilter();
    }

    @Bean
    public FilterRegistrationBean<MetricsFilter> metricsFilterFilterRegistrationBean(MetricsFilter metricsFilter) {
        log.info("MetricsFilter order: {}", Integer.MIN_VALUE);
        FilterRegistrationBean<MetricsFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(metricsFilter);
        filterRegistrationBean.setOrder(Integer.MIN_VALUE);
        filterRegistrationBean.addUrlPatterns(API_PREFIX + "/*");
        return filterRegistrationBean;
    }
}
