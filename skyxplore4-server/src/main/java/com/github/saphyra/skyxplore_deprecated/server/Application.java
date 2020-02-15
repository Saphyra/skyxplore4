package com.github.saphyra.skyxplore_deprecated.server;

import com.github.saphyra.authservice.auth.EnableAuthService;
import com.github.saphyra.authservice.redirection.EnableRedirection;
import com.github.saphyra.encryption.EnableEncryption;
import com.github.saphyra.exceptionhandling.EnableExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Slf4j
@EnableEncryption
@EnableExceptionHandler
@EnableAuthService
@EnableRedirection
@EnableJpaRepositories(basePackages = "com.github.saphyra.skyxplore_deprecated")
@EntityScan(basePackages = "com.github.saphyra.skyxplore_deprecated")
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.github.saphyra.skyxplore_deprecated")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
