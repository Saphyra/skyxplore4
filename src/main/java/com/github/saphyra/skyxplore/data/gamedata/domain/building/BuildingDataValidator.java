package com.github.saphyra.skyxplore.data.gamedata.domain.building;

import com.github.saphyra.skyxplore.data.base.DataValidator;
import com.github.saphyra.skyxplore.data.gamedata.domain.ConstructionRequirementsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class BuildingDataValidator implements DataValidator<BuildingData> {
    private final ConstructionRequirementsValidator constructionRequirementsValidator;
    private final GameDataItemValidator gameDataItemValidator;

    @Override
    public void validate(BuildingData buildingData) {
        gameDataItemValidator.validate(buildingData);
        buildingData.getConstructionRequirements().values().forEach(constructionRequirementsValidator::validate);
        requireNonNull(buildingData.getBuildingType(), "BuildingType must not be null.");
    }
}
