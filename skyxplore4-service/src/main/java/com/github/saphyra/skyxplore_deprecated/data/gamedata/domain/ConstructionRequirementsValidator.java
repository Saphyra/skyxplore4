package com.github.saphyra.skyxplore_deprecated.data.gamedata.domain;

import com.github.saphyra.skyxplore_deprecated.data.base.DataValidator;
import com.github.saphyra.skyxplore_deprecated.game.dao.common.ConstructionRequirements;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Component
public class ConstructionRequirementsValidator implements DataValidator<ConstructionRequirements> {
    @Override
    public void validate(ConstructionRequirements item) {
        requireNonNull(item.getRequiredWorkPoints(), "requiredWorkPoints must not be null.");
        if (item.getRequiredWorkPoints() < 1) {
            throw new IllegalStateException("requiredWorkPoints must be higher than 0");
        }
        if (item.getResearchRequirements().stream().anyMatch(Objects::isNull)) {
            throw new NullPointerException("ResearchRequirements must not contain null.");
        }

        requireNonNull(item.getRequiredResources(), "RequiredResources must not be null.");
        if (item.getRequiredResources().entrySet().stream().anyMatch(e -> isNull(e.getValue()))) {
            throw new NullPointerException("RequiredResources must not contain null.");
        }
    }
}
