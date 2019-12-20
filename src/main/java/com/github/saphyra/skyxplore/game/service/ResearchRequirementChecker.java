package com.github.saphyra.skyxplore.game.service;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.game.dao.map.star.Research;
import com.github.saphyra.skyxplore.game.dao.map.star.Star;
import com.github.saphyra.skyxplore.game.dao.map.star.StarQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResearchRequirementChecker {
    private final StarQueryService starQueryService;

    public void checkResearchRequirements(UUID starId, List<String> researchRequirements) {
        Star star = starQueryService.findByStarIdAndGameIdAndOwnerId(starId);
        List<String> existingResearches = star.getResearches().stream()
            .map(Research::getDataId)
            .collect(Collectors.toList());

        if (!existingResearches.containsAll(researchRequirements)) {
            throw ExceptionFactory.researchNotPresentException();
        }
    }
}
