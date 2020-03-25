package com.github.saphyra.skyxplore.app.game_data.domain.building.production;

import static java.util.Objects.requireNonNull;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.data.DataValidator;
import com.github.saphyra.skyxplore.app.game_data.domain.building.BuildingDataValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class ProductionBuildingValidator implements DataValidator<Map<String, ProductionBuilding>> {
    private final BuildingDataValidator buildingDataValidator;
    private final ProductionValidator productionValidator;

    @Override
    public void validate(Map<String, ProductionBuilding> item) {
        item.forEach(this::validate);
    }

    private void validate(String key, ProductionBuilding productionBuilding) {
        try {
            log.debug("Validating ProductionBuilding with key {}", key);
            buildingDataValidator.validate(productionBuilding);
            requireNonNull(productionBuilding.getWorkers(), "Workers must not be null.");
            if (productionBuilding.getWorkers() < 1) {
                throw new IllegalStateException("Workers must be higher than 0");
            }
            requireNonNull(productionBuilding.getPrimarySurfaceType(), "PrimarySurfaceType must not be null.");
            requireNonNull(productionBuilding.getPlaceableSurfaceTypes(), "PlaceableSurfaceTypes must not be null.");
            productionValidator.validate(productionBuilding.getGives());
        } catch (Exception e) {
            throw new IllegalStateException("Invalid data with key " + key, e);
        }
    }
}
