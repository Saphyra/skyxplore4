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
    @Bean
    public FilterRegistrationBean<ContextFilter> contextFilterFilterRegistrationBean(ContextFilter contextFilter) {
        log.info("ContextFilter order: {}", Integer.MIN_VALUE);
        FilterRegistrationBean<ContextFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(contextFilter);
        filterRegistrationBean.setOrder(Integer.MIN_VALUE);
        filterRegistrationBean.addUrlPatterns(
                API_PREFIX + "/*",
                WEB_PREFIX + "/*"
        );
        return filterRegistrationBean;
    }
}
