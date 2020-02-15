package com.github.saphyra.skyxplore_deprecated.platform.storage;

import com.github.saphyra.skyxplore_deprecated.common.UuidConverter;
import com.github.saphyra.skyxplore_deprecated.platform.storage.domain.Storage;
import com.github.saphyra.skyxplore_deprecated.platform.storage.domain.StorageKey;
import com.github.saphyra.skyxplore_deprecated.platform.storage.domain.StorageKeyId;
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
