package com.github.saphyra.skyxplore.data.gamedata.domain.building;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.ObjectUtils.isEmpty;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.data.base.DataValidator;
import com.github.saphyra.skyxplore.data.gamedata.domain.ConstructionRequirementsValidator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BuildingDataValidator implements DataValidator<BuildingData> {
    private final ConstructionRequirementsValidator constructionRequirementsValidator;
    private final GameDataItemValidator gameDataItemValidator;

    @Override
    public void validate(BuildingData buildingData) {
        gameDataItemValidator.validate(buildingData);
        if (isEmpty(buildingData.getConstructionRequirements())) {
            throw new IllegalStateException("ConstructionRequirements must be filled.");
        }
        buildingData.getConstructionRequirements().values().forEach(constructionRequirementsValidator::validate);
        requireNonNull(buildingData.getBuildingType(), "BuildingType must not be null.");
    }
}
