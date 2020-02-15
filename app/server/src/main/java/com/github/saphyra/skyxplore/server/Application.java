package com.github.saphyra.skyxplore.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*@EnableEncryption
@EnableExceptionHandler
@EnableAuthService
@EnableAspectJAutoProxy*/
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
