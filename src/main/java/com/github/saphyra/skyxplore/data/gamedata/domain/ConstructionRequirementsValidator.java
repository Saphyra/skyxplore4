package com.github.saphyra.skyxplore.data.gamedata.domain;

import com.github.saphyra.skyxplore.data.base.DataValidator;
import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Component
public class ConstructionRequirementsValidator implements DataValidator<ConstructionRequirements> {
    @Override
    public void validate(ConstructionRequirements item) {
        requireNonNull(item.getRequiredWorkPoints(), "requiredWorkPoints must not be null.");
        if (item.getResearchRequirements().stream().anyMatch(Objects::isNull)) {
            throw new NullPointerException("ResearchRequirements must not contain null.");
        }

        if (item.getRequiredResources().entrySet().stream().anyMatch(e -> isNull(e.getValue()))) {
            throw new NullPointerException("RequiredResources must not contain null.");
        }
    }
}
