package com.github.saphyra.skyxplore.app.domain.storage;

import com.github.saphyra.skyxplore.app.domain.storage.domain.Storage;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageKey;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageKeyId;
import com.github.saphyra.skyxplore.common.utils.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StorageFactory {
    private final UuidConverter uuidConverter;

    public Storage create(UUID userId, StorageKey key) {
        return create(userId, key, null);
    }

    public Storage create(UUID userId, StorageKey key, String value) {
        return Storage.builder()
            .storageKey(StorageKeyId.builder()
                .userId(uuidConverter.convertDomain(userId))
                .storageKey(key)
                .build())
            .value(value)
            .build();
    }
}
