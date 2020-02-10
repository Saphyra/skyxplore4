package com.github.saphyra.skyxplore.game.newround.order.processor.storagesetting;

import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.newround.production.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static java.util.Objects.isNull;

@Component
@Slf4j
class ProducerSelector {
    Optional<Producer> selectProducer(ProductionOrder order, Map<UUID, Producer> producers, Set<UUID> depletedProducerIds) {
        return isNull(order.getProducerBuildingId())
            ? selectNewProducer(producers, depletedProducerIds)
            : selectGivenProducer(order.getProducerBuildingId(), producers, depletedProducerIds);
    }

    private Optional<Producer> selectNewProducer(Map<UUID, Producer> producers, Set<UUID> depletedProducerIds) {
        return producers.values()
            .stream()
            .filter(producer -> !depletedProducerIds.contains(producer.getId()))
            .findAny();
    }

    private Optional<Producer> selectGivenProducer(UUID producerBuildingId, Map<UUID, Producer> producers, Set<UUID> depletedProducerIds) {
        log.info("producerBuildingId: {}, producers: {}, depletedProducerIds: {}", producerBuildingId, producers, depletedProducerIds);
        return Optional.of(producers.get(producerBuildingId))
            .filter(producer -> !depletedProducerIds.contains(producer.getId()));
    }
}
