package com.github.saphyra.skyxplore.platform.metrics;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@Aspect
@Slf4j
@ConditionalOnProperty(value = "metrics.cacheRepository.enabled", havingValue = "true")
public class CacheRepositoryMetrics {
    @Pointcut("within(com.github.saphyra.skyxplore.game.dao.common.cache.CacheRepository+)")
    public void cacheRepository() {
    }

    @Around("cacheRepository()")
    public Object logQueryTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object retVal = joinPoint.proceed();
        log.info("Call {} with arguments {} is returned {}", joinPoint.getSignature().toShortString(), Arrays.asList(joinPoint.getArgs()), retVal);
        return retVal;
    }
}
