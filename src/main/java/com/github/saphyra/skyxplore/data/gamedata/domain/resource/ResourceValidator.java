package com.github.saphyra.skyxplore.data.gamedata.domain.resource;

import com.github.saphyra.skyxplore.data.base.DataValidator;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.GameDataItemValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Component
@Slf4j
@RequiredArgsConstructor
public class ResourceValidator implements DataValidator<Map<String, ResourceData>> {
    private final GameDataItemValidator gameDataItemValidator;

    @Override
    public void validate(Map<String, ResourceData> item) {
        item.forEach(this::validate);
    }

    private void validate(String key, ResourceData resource) {
        try {
            log.debug("Validating Resource with key {}", key);
            gameDataItemValidator.validate(resource);

            requireNull(resource.getBuildable(), "Buildable must be null.");
            requireNull(resource.getConstructionRequirements(), "ConstructionRequirements must be null.");

            requireNonNull(resource.getStorageType(), "StorageType must not be null.");
        } catch (Exception e) {
            throw new IllegalStateException("Invalid data with key " + key, e);
        }
    }

    private void requireNull(Object o, String message) {
        if (!isNull(o)) {
            throw new IllegalArgumentException(message);
        }
    }
}
