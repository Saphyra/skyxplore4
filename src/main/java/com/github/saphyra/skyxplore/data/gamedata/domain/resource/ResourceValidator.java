package com.github.saphyra.skyxplore.data.gamedata.domain.resource;

import com.github.saphyra.skyxplore.data.base.DataValidator;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.GameDataItemValidator;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuildingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Objects.requireNonNull;

@Component
@Slf4j
@RequiredArgsConstructor
public class ResourceValidator implements DataValidator<Map<String, ResourceData>> {
    private final GameDataItemValidator gameDataItemValidator;
    private final ProductionBuildingService productionBuildingService;

    @Override
    public void validate(Map<String, ResourceData> item) {
        item.forEach(this::validate);
    }

    private void validate(String key, ResourceData resource) {
        try {
            log.debug("Validating Resource with key {}", key);
            gameDataItemValidator.validate(resource);

            requireNonNull(resource.getStorageType(), "StorageType must not be null.");

            if (!hasProducer(resource)) {
                throw new IllegalStateException("Producer required.");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Invalid data with key " + key, e);
        }
    }

    private boolean hasProducer(ResourceData resource) {
        return productionBuildingService.values().stream().flatMap(productionBuilding -> productionBuilding.getGives().keySet().stream()).anyMatch(s -> s.equals(resource.getId()));
    }
}
