package com.github.saphyra.skyxplore.platform.storage.locale;

import com.github.saphyra.skyxplore.platform.storage.domain.Storage;
import com.github.saphyra.skyxplore.platform.storage.domain.StorageKey;
import com.github.saphyra.skyxplore.platform.storage.domain.StorageKeyId;
import com.github.saphyra.skyxplore.platform.storage.domain.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Component
public class LocaleQueryService {
    private final StorageRepository storageRepository;

    Optional<String> getLocale(UUID userId) {
        return storageRepository.findById(new StorageKeyId(userId, StorageKey.LOCALE))
            .map(Storage::getValue);
    }
}
