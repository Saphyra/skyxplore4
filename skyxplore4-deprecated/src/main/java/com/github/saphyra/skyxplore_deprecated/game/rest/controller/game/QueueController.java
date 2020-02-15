package com.github.saphyra.skyxplore_deprecated.game.rest.controller.game;

import com.github.saphyra.skyxplore_deprecated.common.OneParamRequest;
import com.github.saphyra.skyxplore_deprecated.game.common.interfaces.QueueType;
import com.github.saphyra.skyxplore_deprecated.game.rest.request.UpdatePriorityRequest;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.queue.QueueView;
import com.github.saphyra.skyxplore_deprecated.game.service.queue.QueueQueryService;
import com.github.saphyra.skyxplore_deprecated.game.service.queue.QueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.API_PREFIX;

@RestController
@RequiredArgsConstructor
@Slf4j
public class QueueController {
    private static final String CANCEL_ITEM_MAPPING = API_PREFIX + "/game/star/{starId}/queue/{queueItemId}";
    private static final String GET_QUEUE_MAPPING = API_PREFIX + "/game/star/{starId}/queue";
    private static final String UPDATE_PRIORITY_MAPPING = API_PREFIX + "/game/star/{starId}/queue/{queueItemId}";

    private final QueueService queueService;
    private final QueueQueryService queueQueryService;

    @DeleteMapping(CANCEL_ITEM_MAPPING)
    void cancelItem(
        @PathVariable("starId") UUID starId,
        @PathVariable("queueItemId") UUID queueItemId,
        @Valid @RequestBody OneParamRequest<QueueType> queueType
    ) {
        log.info("Cancelling queueItem {} with queueType {}", queueItemId, queueType.getValue());
        queueService.cancel(starId, queueItemId, queueType.getValue());
    }

    @GetMapping(GET_QUEUE_MAPPING)
    List<QueueView> getQueue(
        @PathVariable("starId") UUID starId
    ) {
        log.info("Querying queue of star {}", starId);
        return queueQueryService.getQueueOfStar(starId);
    }

    @PostMapping(UPDATE_PRIORITY_MAPPING)
    void updatePriority(
        @PathVariable("starId") UUID starId,
        @PathVariable("queueItemId") UUID queueItemId,
        @Valid @RequestBody UpdatePriorityRequest request
    ) {
        log.info("Updating priority of queueItem {} with request {}", queueItemId, request);
        queueService.updatePriority(starId, queueItemId, request);
    }
}
