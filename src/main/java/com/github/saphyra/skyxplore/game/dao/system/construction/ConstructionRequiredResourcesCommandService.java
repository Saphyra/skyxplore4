package com.github.saphyra.skyxplore.game.dao.system.construction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConstructionRequiredResourcesCommandService {
    private final ConstructionResourceRequirementDao constructionResourceRequirementDao;

    public void save(ConstructionResourceRequirement constructionResourceRequirement) {
        constructionResourceRequirementDao.save(constructionResourceRequirement);
    }
}
