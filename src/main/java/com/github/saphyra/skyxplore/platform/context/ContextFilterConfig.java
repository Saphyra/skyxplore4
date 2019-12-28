package com.github.saphyra.skyxplore.platform.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;
import static com.github.saphyra.skyxplore.common.RequestConstants.WEB_PREFIX;

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

    @Bean
    public FilterRegistrationBean<ContextValidationFilter> contextValidationFilterFilterRegistrationBean(ContextValidationFilter contextValidationFilter) {
        log.info("ContextValidationFilter order: {}", FILTER_ORDER + 1);
        FilterRegistrationBean<ContextValidationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(contextValidationFilter);
        filterRegistrationBean.setOrder(FILTER_ORDER);
        filterRegistrationBean.addUrlPatterns(
            API_PREFIX + "/game/*"
        );
        return filterRegistrationBean;
    }
}
