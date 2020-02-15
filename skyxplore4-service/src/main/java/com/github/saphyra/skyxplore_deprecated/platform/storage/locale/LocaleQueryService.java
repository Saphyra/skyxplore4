package com.github.saphyra.skyxplore_deprecated.platform.storage.locale;

import com.github.saphyra.skyxplore_deprecated.common.UuidConverter;
import com.github.saphyra.skyxplore_deprecated.platform.storage.domain.Storage;
import com.github.saphyra.skyxplore_deprecated.platform.storage.domain.StorageKey;
import com.github.saphyra.skyxplore_deprecated.platform.storage.domain.StorageKeyId;
import com.github.saphyra.skyxplore_deprecated.platform.storage.domain.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
