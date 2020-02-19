package com.github.saphyra.skyxplore.common.exception_handling.localization;

import java.util.Optional;
import java.util.UUID;

public interface LocaleProvider {
    Optional<String> getByUserId(UUID userId);
}
