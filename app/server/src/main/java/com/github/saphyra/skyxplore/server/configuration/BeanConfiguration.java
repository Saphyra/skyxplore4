package com.github.saphyra.skyxplore.server.configuration;

import com.github.saphyra.skyxplore.common.data.CommonDataScanner;
import com.github.saphyra.skyxplore.common.utils.CommonUtilsScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
    basePackages = {
        "com.github.saphyra.util"
    },
    basePackageClasses = {
        CommonDataScanner.class,
        CommonUtilsScanner.class
    })
public class BeanConfiguration {
}
