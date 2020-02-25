package com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.research;

import com.github.saphyra.skyxplore.app.common.data.DataValidator;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.ConstructionRequirementsValidator;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.GameDataItemValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.ObjectUtils.isEmpty;

@Component
@Slf4j
@RequiredArgsConstructor
public class ResearchDataValidator implements DataValidator<Map<String, ResearchData>> {
    private final ConstructionRequirementsValidator constructionRequirementsValidator;
    private final GameDataItemValidator gameDataItemValidator;
    private final UsedInValidator usedInValidator;

    @Override
    public void validate(Map<String, ResearchData> item) {
        item.forEach(this::validate);
    }

    private void validate(String key, ResearchData researchData) {
        try {
            gameDataItemValidator.validate(researchData);
            constructionRequirementsValidator.validate(researchData.getConstructionRequirements());
            if (isEmpty(researchData.getUnlocks())) {
                throw new IllegalStateException("Research must unlock at least 1 item.");
            }
            usedInValidator.validate(researchData.getUnlocks());

            requireNonNull(researchData.getUsedIn(), "UsedIn must not be null.");
            if (!isEmpty(researchData.getUsedIn())) {
                usedInValidator.validate(researchData.getUsedIn());
            }
            log.debug("Validating ResearchData with key {}", key);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid data with key " + key, e);
        }
    }
}
