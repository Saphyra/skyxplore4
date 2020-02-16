package com.github.saphyra.skyxplore.app.domain.user;

import com.github.saphyra.encryption.impl.PasswordService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainUserBeanConfiguration {
    @ConditionalOnMissingBean
    @Bean
    public PasswordService passwordService(){
        return new PasswordService();
    }
}
