package com.github.saphyra.skyxplore_deprecated.game.service.system.priority;

import com.github.saphyra.skyxplore_deprecated.common.OneParamRequest;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.priority.PriorityType;
import com.github.saphyra.skyxplore_deprecated.game.service.system.priority.update.PriorityUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.API_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PriorityController {
    private static final String UPDATE_PRIORITY_MAPPING = API_PREFIX + "/game/star/{starId}/priority/{type}";

    private final PriorityUpdateService priorityUpdateService;

    @PostMapping(UPDATE_PRIORITY_MAPPING)
    void updatePriority(
        @PathVariable("starId") UUID starId,
        @PathVariable("type") PriorityType type,
        @RequestBody @Valid OneParamRequest<Integer> priority
    ) {
        log.info("Updating priority of {}:{} to {}", starId, type, priority.getValue());
        priorityUpdateService.updatePriority(starId, type, priority.getValue());
    }
}
