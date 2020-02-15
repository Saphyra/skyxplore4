package com.github.saphyra.skyxplore_deprecated.platform.metrics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.API_PREFIX;

@Configuration
@ConditionalOnProperty(value = "metrics.rest.enabled", havingValue = "true")
@Slf4j
public class RestMetricsConfig {
    @Bean
    public RestMetricsFilter metricsFilter() {
        return new RestMetricsFilter();
    }

    @Bean
    public FilterRegistrationBean<RestMetricsFilter> metricsFilterFilterRegistrationBean(RestMetricsFilter restMetricsFilter) {
        log.info("MetricsFilter order: {}", Integer.MIN_VALUE);
        FilterRegistrationBean<RestMetricsFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(restMetricsFilter);
        filterRegistrationBean.setOrder(Integer.MIN_VALUE);
        filterRegistrationBean.addUrlPatterns(API_PREFIX + "/*");
        return filterRegistrationBean;
    }
}
