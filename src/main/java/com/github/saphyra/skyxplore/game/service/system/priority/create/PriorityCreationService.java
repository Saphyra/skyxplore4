package com.github.saphyra.skyxplore.game.service.system.priority.create;

import com.github.saphyra.skyxplore.game.common.DomainSaverService;
import com.github.saphyra.skyxplore.game.dao.map.star.Star;
import com.github.saphyra.skyxplore.game.dao.system.priority.Priority;
import com.github.saphyra.skyxplore.game.dao.system.priority.PriorityType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriorityCreationService {
    private final DomainSaverService domainSaverService;
    private final PriorityFactory priorityFactory;

    public void createForStars(List<Star> createdStars) {
        List<Priority> priorities = createdStars.stream()
            .flatMap(this::createForStar)
            .collect(Collectors.toList());

        domainSaverService.addAll(priorities);
    }

    private Stream<Priority> createForStar(Star star) {
        return Arrays.stream(PriorityType.values())
            .map(priorityType -> priorityFactory.create(star, priorityType));
    }
}
