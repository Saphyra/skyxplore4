package com.github.saphyra.skyxplore_deprecated.game.dao.common.cache;

import com.github.saphyra.skyxplore_deprecated.common.ExecutorServiceBean;
import com.github.saphyra.skyxplore_deprecated.game.dao.common.cache.trigger.FullSyncTrigger;
import com.github.saphyra.skyxplore_deprecated.game.dao.common.cache.trigger.ProcessDeletionsTrigger;
import com.github.saphyra.skyxplore_deprecated.game.dao.common.cache.trigger.SynchChangesTrigger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
@ConditionalOnProperty(value = "com.github.saphyra.skyxplore.cacheRepository.enabled", havingValue = "true")
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
