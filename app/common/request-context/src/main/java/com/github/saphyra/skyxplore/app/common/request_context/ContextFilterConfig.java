package com.github.saphyra.skyxplore.app.common.request_context;

import static com.github.saphyra.skyxplore.app.common.config.RequestConstants.API_PREFIX;
import static com.github.saphyra.skyxplore.app.common.config.RequestConstants.WEB_PREFIX;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ContextFilterConfig {
    private static final int FILTER_ORDER = 110;

    @Bean
    public FilterRegistrationBean<ContextFilter> contextFilterFilterRegistrationBean(ContextFilter contextFilter) {
        log.info("ContextFilter order: {}", FILTER_ORDER);
        FilterRegistrationBean<ContextFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(contextFilter);
        filterRegistrationBean.setOrder(FILTER_ORDER);
        filterRegistrationBean.addUrlPatterns(
            API_PREFIX + "/*",
            WEB_PREFIX + "/*"
        );
        return filterRegistrationBean;
    }
}
