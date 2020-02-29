package com.github.saphyra.skyxplore.server.app.configuration;

import com.github.saphyra.authservice.auth.EnableAuthService;
import com.github.saphyra.authservice.redirection.EnableRedirection;
import com.github.saphyra.encryption.EnableEncryption;
import com.github.saphyra.exceptionhandling.EnableExceptionHandler;
import com.github.saphyra.skyxplore.app.auth.AuthScanner;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextScanner;
import com.github.saphyra.skyxplore.app.common.service.CommonServiceScanner;
import com.github.saphyra.skyxplore.app.domain.game.GameScanner;
import com.github.saphyra.skyxplore.app.domain.storage.StorageScanner;
import com.github.saphyra.skyxplore.app.domain.user.DomainUserScanner;
import com.github.saphyra.skyxplore.app.common.config.CommonConfigScanner;
import com.github.saphyra.skyxplore.app.common.data.CommonDataScanner;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorHandlingScanner;
import com.github.saphyra.skyxplore.app.common.utils.CommonUtilsScanner;
import com.github.saphyra.skyxplore.web.WebScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
    basePackages = {
        "com.github.saphyra.util"
    },
    basePackageClasses = {
        AuthScanner.class,
        CommonDataScanner.class,
        CommonConfigScanner.class,
        CommonServiceScanner.class,
        CommonUtilsScanner.class,
        DomainUserScanner.class,
        ErrorHandlingScanner.class,
        GameScanner.class,
        RequestContextScanner.class,
        StorageScanner.class,
        WebScanner.class
    })
@EnableRedirection
@EnableAuthService
@EnableExceptionHandler
@EnableEncryption
public class BeanConfiguration {
}
