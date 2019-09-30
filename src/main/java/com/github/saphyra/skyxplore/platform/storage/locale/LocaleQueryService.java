package com.github.saphyra.skyxplore.platform.storage.locale;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.platform.storage.domain.Storage;
import com.github.saphyra.skyxplore.platform.storage.domain.StorageKey;
import com.github.saphyra.skyxplore.platform.storage.domain.StorageKeyId;
import com.github.saphyra.skyxplore.platform.storage.domain.StorageRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Component
class LocaleQueryService {
    private final StorageRepository storageRepository;
    private final UuidConverter uuidConverter;

    Optional<String> getLocale(UUID userId) {
        return storageRepository.findById(new StorageKeyId(uuidConverter.convertDomain(userId), StorageKey.LOCALE))
            .map(Storage::getValue);
    }
}
