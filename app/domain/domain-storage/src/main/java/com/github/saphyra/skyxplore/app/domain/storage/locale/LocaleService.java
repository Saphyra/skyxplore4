package com.github.saphyra.skyxplore.app.domain.storage.locale;

import com.github.saphyra.skyxplore.app.domain.storage.StorageFactory;
import com.github.saphyra.skyxplore.app.domain.storage.domain.Storage;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageKey;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageKeyId;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageRepository;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
class LocaleService {
    private final LocaleCache localeCache;
    private final StorageRepository storageRepository;
    private final StorageFactory storageFactory;
    private final UuidConverter uuidConverter;

    void setLocale(UUID userId, String locale) {
        Storage storage = getOrCreate(userId);
        storage.setValue(locale);
        storageRepository.save(storage);
        localeCache.invalidate(userId);
        log.info("Locale for user {} is set to {}", userId, locale);
    }

    private Storage getOrCreate(UUID userId) {
        StorageKeyId storageKeyId = new StorageKeyId(
            uuidConverter.convertDomain(userId),
            StorageKey.LOCALE
        );
        return storageRepository.findById(storageKeyId)
            .orElseGet(() -> storageFactory.create(userId, StorageKey.LOCALE));
    }
}
