package com.github.saphyra.skyxplore.app.common.dao;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.event.FullSyncTrigger;
import com.github.saphyra.skyxplore.app.common.event.ProcessDeletionsTrigger;
import com.github.saphyra.skyxplore.app.common.event.SynchChangesTrigger;
import com.github.saphyra.skyxplore.app.common.service.ExecutorServiceBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
@ConditionalOnProperty(value = "com.github.saphyra.skyxplore.cacheRepository.enabled", havingValue = "true")
//TODO unit test
public class CacheSyncHandler {
    private final List<CacheRepository<?, ?, ?, ?>> repositories;
    private final ExecutorServiceBean executorServiceBean;

    @Scheduled(fixedRateString = "${com.github.saphyra.skyxplore.cacheRepository.fullSyncExecutionIntervalInMilliseconds}")
    @EventListener(classes = FullSyncTrigger.class)
    public void fullSync() {
        executorServiceBean.execute(
            () -> {
                log.info("Executing scheduled sync for CacheRepositories...");
                repositories.stream()
                    .parallel()
                    .forEach(CacheRepository::fullSync);
                log.info("Scheduled sync is finished.");
            }
        );
    }

    @EventListener(classes = SynchChangesTrigger.class)
    public void syncChanges() {
        executorServiceBean.execute(
            () -> repositories.stream()
                .parallel()
                .forEach(CacheRepository::syncChanges)
        );
    }

    @EventListener(classes = ProcessDeletionsTrigger.class)
    public void processDeletions() {
        executorServiceBean.execute(() -> repositories
            .stream()
            .parallel()
            .forEach(CacheRepository::processDeletions));
    }
}
