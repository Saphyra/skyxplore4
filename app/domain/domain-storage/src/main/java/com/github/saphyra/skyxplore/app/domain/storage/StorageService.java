package com.github.saphyra.skyxplore.app.domain.storage;

import com.github.saphyra.skyxplore.app.common.event.UserDeletedEvent;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageRepository;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class StorageService {
    private final StorageRepository storageRepository;
    private final UuidConverter uuidConverter;

    @EventListener
    void deleteStorage(UserDeletedEvent event) {
        storageRepository.deleteByUserId(uuidConverter.convertDomain(event.getUserId()));
    }
}
