package com.github.saphyra.skyxplore_deprecated.platform.metrics;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

@Configuration
@Aspect
@Slf4j
@ConditionalOnProperty(value = "metrics.repository.enabled", havingValue = "true")
public class RepositoryMetrics {
    @Around("execution(* com.github.saphyra.skyxplore.game.dao..*QueryService.*(..)) || execution(* com.github.saphyra.skyxplore.game.dao..*CommandService.*(..))")
    public Object logQueryTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch(joinPoint.getSignature().toShortString());
        try {
            stopWatch.start();
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            log.info("Call {} is finished in {} ms", stopWatch.getId(), stopWatch.getTotalTimeMillis());
        }
    }
}
