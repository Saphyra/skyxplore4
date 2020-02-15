package com.github.saphyra.skyxplore_deprecated.game.service.system.priority;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.priority.Priority;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.priority.PriorityQueryService;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.system.PriorityView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriorityViewQueryService {
    private final PriorityQueryService priorityQueryService;

    public List<PriorityView> getByStarId(UUID starId) {
        return priorityQueryService.getByStarIdAndPlayerId(starId)
            .stream()
            .map(this::convert)
            .collect(Collectors.toList());
    }

    private PriorityView convert(Priority priority) {
        return PriorityView.builder()
            .type(priority.getType())
            .priority(priority.getValue())
            .build();
    }
}
