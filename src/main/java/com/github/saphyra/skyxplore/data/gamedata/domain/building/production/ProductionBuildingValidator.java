package com.github.saphyra.skyxplore.data.gamedata.domain.building.production;

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
            requireNonNull(productionBuilding.getPrimarySurfaceType(), "PrimarySurfaceType must not be null.");
            requireNonNull(productionBuilding.getPlaceableSurfaceTypes(), "PlaceableSurfaceTypes must not be null.");
            requireNonNull(productionBuilding.getCache(), "Cache must not be null.");
           productionValidator.validate(productionBuilding.getGives());
        } catch (Exception e) {
            throw new IllegalStateException("Invalid data with key " + key, e);
        }
    }
}
