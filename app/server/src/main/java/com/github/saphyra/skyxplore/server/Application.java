package com.github.saphyra.skyxplore.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
/*@EnableEncryption
@EnableExceptionHandler
@EnableAuthService
@EnableAspectJAutoProxy*/
public class Application {
    public static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Application.class, args);
    }
}
