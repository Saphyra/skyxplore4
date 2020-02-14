package com.github.saphyra.skyxplore.data.gamedata.domain.building.storage;

import com.github.saphyra.skyxplore.data.base.DataValidator;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.BuildingDataValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class StorageBuildingValidator implements DataValidator<Map<String, StorageBuilding>> {
    private final BuildingDataValidator buildingDataValidator;

    @Override
    public void validate(Map<String, StorageBuilding> item) {
        item.forEach(this::validate);
    }

    private void validate(String key, StorageBuilding storageBuilding) {
        try {
            log.debug("Validating StorageBuilding with key {}", key);
            buildingDataValidator.validate(storageBuilding);
            requireNonNull(storageBuilding.getStores(), "Stores must not be null.");
            requireNonNull(storageBuilding.getCapacity(), "Capacity must not be null.");
        } catch (Exception e) {
            throw new IllegalStateException("Invalid data with key " + key, e);
        }
    }
}
