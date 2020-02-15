package com.github.saphyra.skyxplore_deprecated.game.service.system.priority.update;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.priority.Priority;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.priority.PriorityCommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.priority.PriorityQueryService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.priority.PriorityType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriorityUpdateService {
    private final PriorityCommandService priorityCommandService;
    private final PriorityQueryService priorityQueryService;

    public void updatePriority(UUID starId, PriorityType type, Integer value) {
        Priority priority = priorityQueryService.findByStarIdAndPriorityTypeAndPlayerIdValidated(starId, type);
        priority.setValue(value);
        priorityCommandService.save(priority);
    }
}
