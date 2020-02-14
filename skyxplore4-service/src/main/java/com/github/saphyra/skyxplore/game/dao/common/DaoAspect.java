package com.github.saphyra.skyxplore.game.dao.common;

import com.github.saphyra.skyxplore.game.dao.common.cache.SettablePersistable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.Collection;

@Configuration
@Aspect
@Slf4j
public class DaoAspect {

    @Pointcut("within(com.github.saphyra.converter.ConverterBase+)")
    public void converter() {
    }

    @Pointcut("execution()")
    public void domainConversion() {
    }

    @Around("execution(* com.github.saphyra.converter.ConverterBase+.*Domain*(..))")
    public Object setIsNewToFalseIfPersistable(ProceedingJoinPoint joinPoint) throws Throwable {
        Object domain = joinPoint.getArgs()[0];
        Object retVal = joinPoint.proceed();
        if (retVal instanceof Collection) {
            Collection retVals = (Collection) retVal;
            if (!retVals.isEmpty() && retVals.iterator().next() instanceof SettablePersistable) {
                for (Object o : retVals) {
                    ((SettablePersistable) o).setNew(false);
                }
                Collection domains = (Collection) domain;
                for (Object o : domains) {
                    updateField(o);
                }
            }
        }
        if (retVal instanceof SettablePersistable) {
            ((SettablePersistable) retVal).setNew(false);
            updateField(domain);
        }
        return retVal;
    }

    private void updateField(Object domain) throws NoSuchFieldException, IllegalAccessException {
        log.debug("Updating new field of {} to false.", domain.getClass().getSimpleName());
        Field field = domain.getClass().getDeclaredField("isNew");
        field.setAccessible(true);
        field.setBoolean(domain, false);
    }
}
