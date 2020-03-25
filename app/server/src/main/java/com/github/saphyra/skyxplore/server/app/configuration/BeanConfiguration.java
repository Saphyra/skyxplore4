package com.github.saphyra.skyxplore.server.app.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.github.saphyra.authservice.auth.EnableAuthService;
import com.github.saphyra.authservice.redirection.EnableRedirection;
import com.github.saphyra.encryption.EnableEncryption;
import com.github.saphyra.exceptionhandling.EnableExceptionHandler;
import com.github.saphyra.skyxplore.app.auth.AuthScanner;
import com.github.saphyra.skyxplore.app.common.config.CommonConfigScanner;
import com.github.saphyra.skyxplore.app.common.dao.configuration.CommonDaoScanner;
import com.github.saphyra.skyxplore.app.common.data.CommonDataScanner;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorHandlingScanner;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextScanner;
import com.github.saphyra.skyxplore.app.common.service.CommonServiceScanner;
import com.github.saphyra.skyxplore.app.common.utils.CommonUtilsScanner;
import com.github.saphyra.skyxplore.app.domain.coordinate.CoordinateScanner;
import com.github.saphyra.skyxplore.app.domain.data.DomainDataScanner;
import com.github.saphyra.skyxplore.app.domain.game.GameScanner;
import com.github.saphyra.skyxplore.app.domain.player.PlayerScanner;
import com.github.saphyra.skyxplore.app.domain.star.StarScanner;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnectionScanner;
import com.github.saphyra.skyxplore.app.domain.storage.StorageScanner;
import com.github.saphyra.skyxplore.app.domain.surface.SurfaceScanner;
import com.github.saphyra.skyxplore.app.domain.user.DomainUserScanner;
import com.github.saphyra.skyxplore.app.rest.RestScanner;
import com.github.saphyra.skyxplore.app.service.common.ServiceCommonScanner;
import com.github.saphyra.skyxplore.app.service.game.ServiceGameScanner;
import com.github.saphyra.skyxplore.app.service.game_creation.GameCreationScanner;
import com.github.saphyra.skyxplore.app.service.query.QueryScanner;
import com.github.saphyra.skyxplore.app.service.star.StarServiceScanner;
import com.github.saphyra.skyxplore.web.WebScanner;

@Configuration
@ComponentScan(
    basePackages = {
        "com.github.saphyra.util"
    },
    basePackageClasses = {
        AuthScanner.class,
        CommonDataScanner.class,
        CommonDaoScanner.class,
        CommonConfigScanner.class,
        CommonServiceScanner.class,
        CommonUtilsScanner.class,
        CoordinateScanner.class,
        DomainDataScanner.class,
        DomainUserScanner.class,
        ErrorHandlingScanner.class,
        GameCreationScanner.class,
        GameScanner.class,
        PlayerScanner.class,
        QueryScanner.class,
        RequestContextScanner.class,
        RestScanner.class,
        ServiceCommonScanner.class,
        ServiceGameScanner.class,
        StarConnectionScanner.class,
        StarScanner.class,
        StarServiceScanner.class,
        StorageScanner.class,
        SurfaceScanner.class,
        WebScanner.class
    })
@EnableRedirection
@EnableAuthService
@EnableExceptionHandler
@EnableEncryption
public class BeanConfiguration {
}
