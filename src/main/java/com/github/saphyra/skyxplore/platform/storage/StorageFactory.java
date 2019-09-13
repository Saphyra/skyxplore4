package com.github.saphyra.skyxplore.platform.storage;

import com.github.saphyra.skyxplore.platform.storage.domain.Storage;
import com.github.saphyra.skyxplore.platform.storage.domain.StorageKey;
import com.github.saphyra.skyxplore.platform.storage.domain.StorageKeyId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StorageFactory {
    public Storage create(UUID userId, StorageKey key) {
        return create(userId, key, null);
    }

    public Storage create(UUID userId, StorageKey key, String value) {
        return Storage.builder()
            .storageKey(StorageKeyId.builder()
                .userId(userId)
                .settingKey(key)
                .build())
            .value(value)
            .build();
    }
}
