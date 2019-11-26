package com.github.saphyra.skyxplore.game.service.system.costruction;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ConstructionController {
    private static final String CANCEL_CONSTRUCTION_MAPPING = API_PREFIX + "/game/construction/{constructionId}";

    private final CancelConstructionService cancelConstructionService;

    @DeleteMapping(CANCEL_CONSTRUCTION_MAPPING)
    void deleteConstruction(
        @PathVariable("constructionId") UUID constructionId
    ) {
        log.info("Cancelling construction {}", constructionId);
        cancelConstructionService.cancelConstruction(constructionId);
    }
}
