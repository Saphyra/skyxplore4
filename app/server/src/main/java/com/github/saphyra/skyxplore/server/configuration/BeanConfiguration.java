package com.github.saphyra.skyxplore.server.configuration;

import com.github.saphyra.authservice.redirection.EnableRedirection;
import com.github.saphyra.skyxplore.common.data.CommonDataScanner;
import com.github.saphyra.skyxplore.common.utils.CommonUtilsScanner;
import com.github.saphyra.skyxplore.web.WebScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
    basePackages = {
        "com.github.saphyra.util"
    },
    basePackageClasses = {
        CommonDataScanner.class,
        CommonUtilsScanner.class,
        WebScanner.class
    })
@EnableRedirection
public class BeanConfiguration {
}
