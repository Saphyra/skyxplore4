package com.github.saphyra.skyxplore.game.newround.order.processor;

import com.github.saphyra.skyxplore.game.dao.game.GameQueryService;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderCommandService;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.Resource;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSetting;
import com.github.saphyra.skyxplore.game.newround.order.StorageSettingOrder;
import com.github.saphyra.skyxplore.game.newround.production.Producer;
import com.github.saphyra.skyxplore.game.newround.production.ProducerQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO refactor - split
public class StorageSettingOrderProcessor {
    private static final int BATCH_SIZE = 100;

    private final GameQueryService gameQueryService;
    private final ProductionOrderCommandService productionOrderCommandService;
    private final ProductionOrderFactory productionOrderFactory;
    private final ProductionOrderQueryService productionOrderQueryService;
    private final ProducerQueryService producerQueryService;
    private final ResourceCommandService resourceCommandService;
    private final ResourceFactory resourceFactory;

    public void process(StorageSettingOrder settingOrder) {
        StorageSetting storageSetting = settingOrder.getStorageSetting();
        log.info("Processing StorageSettingOrder for StorageSetting {} with priority {}", storageSetting.getStorageSettingId(), settingOrder.getPriority());

        List<ProductionOrder> existingOrders = productionOrderQueryService.getByOrderIdAndPlayerId(storageSetting.getStorageSettingId());

        List<ProductionOrder> newOrders = createNewOrders(settingOrder, existingOrders);
        List<ProductionOrder> productionOrderQueue = mergeAndSort(existingOrders, newOrders);

        Map<UUID, Producer> producers = producerQueryService.getByStarIdAndDataId(storageSetting.getStarId(), storageSetting.getDataId())
            .stream()
            .collect(Collectors.toMap(Producer::getId, Function.identity()));

        Set<UUID> depletedProducerIds = new HashSet<>();
        for (ProductionOrder order : productionOrderQueue) {
            Optional<Producer> optionalProducer = selectProducer(order, producers, depletedProducerIds);
            if (optionalProducer.isPresent()) {
                Producer producer = optionalProducer.get();
                boolean depleted = producer.produce(order);
                if (depleted) {
                    depletedProducerIds.add(producer.getId());
                }
                if (order.isReady()) {
                    Resource resource = resourceFactory.createOrUpdate(order, storageSetting.getStarId(), gameQueryService.findByGameIdAndUserIdValidated().getRound());
                    resourceCommandService.save(resource);
                    productionOrderCommandService.delete(order);
                }
            }
            productionOrderCommandService.save(order);
        }
    }

    private List<ProductionOrder> createNewOrders(StorageSettingOrder settingOrder, List<ProductionOrder> existingOrders) {
        int orderRequiredAmount = getOrderRequiredAmount(settingOrder, existingOrders);

        List<ProductionOrder> newOrders = new ArrayList<>();
        for (int amount = orderRequiredAmount; amount > 0; amount -= BATCH_SIZE) {
            newOrders.add(productionOrderFactory.create(settingOrder.getStorageSetting(), Math.min(amount, BATCH_SIZE)));
        }
        return newOrders;
    }

    private int getOrderRequiredAmount(StorageSettingOrder order, List<ProductionOrder> existingOrders) {
        int missingAmount = order.getMissingAmount();
        int orderedAmount = getOrderedAmount(existingOrders);
        return missingAmount - orderedAmount;
    }

    private int getOrderedAmount(List<ProductionOrder> existingOrders) {
        return existingOrders.stream()
            .mapToInt(ProductionOrder::getTargetAmount)
            .sum();
    }

    private List<ProductionOrder> mergeAndSort(List<ProductionOrder> existingOrders, List<ProductionOrder> newOrders) {
        Stream<ProductionOrder> existing = existingOrders.stream()
            .sorted((o1, o2) -> getRemaining(o2) - getRemaining(o1));

        return Stream.concat(existing, newOrders.stream())
            .collect(Collectors.toList());
    }

    private int getRemaining(ProductionOrder order) {
        return order.getTargetAmount() - order.getProducedAmount();
    }

    private Optional<Producer> selectProducer(ProductionOrder order, Map<UUID, Producer> producers, Set<UUID> depletedProducerIds) {
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
        return Optional.of(producers.get(producerBuildingId))
            .filter(producer -> !depletedProducerIds.contains(producer.getId()));
    }
}
