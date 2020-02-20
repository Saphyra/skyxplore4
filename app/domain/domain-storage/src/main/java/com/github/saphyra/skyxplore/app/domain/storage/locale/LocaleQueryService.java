package com.github.saphyra.skyxplore.app.domain.storage.locale;

import com.github.saphyra.skyxplore.app.domain.storage.domain.Storage;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageKey;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageKeyId;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageRepository;
import com.github.saphyra.skyxplore.common.utils.UuidConverter;
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
        StorageKeyId keyId = new StorageKeyId(
            uuidConverter.convertDomain(userId),
            StorageKey.LOCALE
        );
        return storageRepository.findById(keyId)
            .map(Storage::getValue);
    }
}
