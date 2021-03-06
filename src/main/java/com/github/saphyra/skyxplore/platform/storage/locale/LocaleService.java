package com.github.saphyra.skyxplore.platform.storage.locale;

import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.platform.storage.StorageFactory;
import com.github.saphyra.skyxplore.platform.storage.domain.Storage;
import com.github.saphyra.skyxplore.platform.storage.domain.StorageKey;
import com.github.saphyra.skyxplore.platform.storage.domain.StorageKeyId;
import com.github.saphyra.skyxplore.platform.storage.domain.StorageRepository;
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
    private final StorageFactory userSettingsFactory;
    private final UuidConverter uuidConverter;

    void setLocale(UUID userId, String locale) {
        Storage userSettings = getOrCreate(userId);
        userSettings.setValue(locale);
        storageRepository.save(userSettings);
        localeCache.invalidate(userId);
        log.info("Locale for user {} is set to {}", userId, locale);
    }

    private Storage getOrCreate(UUID userId) {
        return storageRepository.findById(new StorageKeyId(uuidConverter.convertDomain(userId), StorageKey.LOCALE))
            .orElseGet(() -> userSettingsFactory.create(userId, StorageKey.LOCALE));
    }
}
