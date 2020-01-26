package com.github.saphyra.skyxplore.game.newround.order.processor;

import com.github.saphyra.skyxplore.data.gamedata.domain.resource.ResourceDataService;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.Resource;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceQueryService;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
class ResourceFactory {
    private final IdGenerator idGenerator;
    private final ResourceDataService resourceDataService;
    private final ResourceQueryService resourceQueryService;

    Resource createOrUpdate(ProductionOrder order, UUID starId, int round) {
        Resource resource = resourceQueryService.findByStarIdAndDataIdAndRoundAndPlayerId(
            starId,
            order.getDataId(),
            round
        ).orElseGet(() -> create(order, starId, round));

        resource.addAmount(order.getTargetAmount());
        return resource;
    }

    private Resource create(ProductionOrder order, UUID starId, Integer round) {
        return Resource.builder()
            .resourceId(idGenerator.randomUUID())
            .gameId(order.getGameId())
            .playerId(order.getPlayerId())
            .storageType(resourceDataService.get(order.getDataId()).getStorageType())
            .dataId(order.getDataId())
            .starId(starId)
            .amount(0)
            .round(round)
            .isNew(true)
            .build();
    }
}
