package com.github.saphyra.skyxplore.data.gamedata.domain.terraforming;

import com.github.saphyra.skyxplore.data.base.DataValidator;
import com.github.saphyra.skyxplore.data.gamedata.domain.ConstructionRequirementsValidator;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.ObjectUtils.isEmpty;

@Component
@Slf4j
@RequiredArgsConstructor
public class TerraformingPossibilitiesValidator implements DataValidator<Map<SurfaceType, TerraformingPossibilities>> {
    private final ConstructionRequirementsValidator constructionRequirementsValidator;

    @Override
    public void validate(Map<SurfaceType, TerraformingPossibilities> item) {
        item.forEach(this::validate);
    }

    private void validate(SurfaceType key, TerraformingPossibilities terraformingPossibilities) {
        try {
            log.debug("Validating ProductionBuilding with key {}", key);
            if (isEmpty(terraformingPossibilities)) {
                throw new IllegalStateException("TerraformingPossibilities must not be empty.");
            }
            terraformingPossibilities.forEach(this::validate);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid data with SurfaceType " + key, e);
        }
    }

    private void validate(TerraformingPossibility terraformingPossibility) {
        requireNonNull(terraformingPossibility, "TerraformingPossibility must not be null.");
        constructionRequirementsValidator.validate(terraformingPossibility.getConstructionRequirements());
        requireNonNull(terraformingPossibility.getSurfaceType());
    }
}
