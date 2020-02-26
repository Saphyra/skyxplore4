package com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.miscellaneous;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.data.DataValidator;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.BuildingDataValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class MiscellaneousBuildingValidator implements DataValidator<Map<String, MiscellaneousBuilding>> {
    private final BuildingDataValidator buildingDataValidator;

    @Override
    public void validate(Map<String, MiscellaneousBuilding> item) {
        item.forEach(this::validate);
    }

    private void validate(String key, MiscellaneousBuilding miscellaneousBuilding) {
        try {
            log.debug("Validating ProductionBuilding with key {}", key);
            buildingDataValidator.validate(miscellaneousBuilding);
            requireNonNull(miscellaneousBuilding.getPlaceableSurfaceTypes(), "PlaceableSurfaceTypes must not be null");
            if (miscellaneousBuilding.getPlaceableSurfaceTypes().stream().anyMatch(Objects::isNull)) {
                throw new NullPointerException("PlaceableSurfaceTypes must not contain null.");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Invalid data with key " + key, e);
        }
    }
}
