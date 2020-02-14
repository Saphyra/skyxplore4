package com.github.saphyra.skyxplore.game.newround.order.processor.storagesetting;

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
import com.github.saphyra.skyxplore.game.newround.production.ProducerSelector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class StorageSettingOrderProcessor {
    private final GameQueryService gameQueryService;
    private final NewOrderCreator newOrderCreator;
    private final ProductionOrderCommandService productionOrderCommandService;
    private final ProductionOrderQueryService productionOrderQueryService;
    private final ProducerQueryService producerQueryService;
    private final ProducerSelector producerSelector;
    private final ResourceCommandService resourceCommandService;
    private final ResourceFactory resourceFactory;

    public void process(StorageSettingOrder settingOrder) {
        StorageSetting storageSetting = settingOrder.getStorageSetting();
        log.info("Processing StorageSettingOrder for StorageSetting {} with priority {} ({} amount of {})", storageSetting.getStorageSettingId(), settingOrder.getPriority(), storageSetting.getTargetAmount(), storageSetting.getDataId());

        List<ProductionOrder> existingOrders = productionOrderQueryService.getByCustomerIdAndPlayerId(storageSetting.getStorageSettingId());

        List<ProductionOrder> newOrders = newOrderCreator.createNewOrders(settingOrder, existingOrders);
        List<ProductionOrder> productionOrderQueue = mergeAndSort(existingOrders, newOrders);

        Map<UUID, Producer> producers = producerQueryService.getByStarIdAndDataId(storageSetting.getStarId(), storageSetting.getDataId())
            .stream()
            .collect(Collectors.toMap(Producer::getId, Function.identity()));
        log.info("Producers for dataId {}: {}", storageSetting.getDataId(), producers);

        Set<UUID> depletedProducerIds = new HashSet<>();
        for (ProductionOrder order : productionOrderQueue) {
            log.info("Producing ProductionOrder {}", order);
            Optional<Producer> optionalProducer = producerSelector.selectProducer(order, producers, depletedProducerIds);
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
                } else {
                    productionOrderCommandService.save(order);
                }
            } else {
                log.info("Available Producer not found for dataId {}", storageSetting.getDataId());
                break;
            }
        }
    }

    private List<ProductionOrder> mergeAndSort(List<ProductionOrder> existingOrders, List<ProductionOrder> newOrders) {
        Stream<ProductionOrder> existing = existingOrders.stream()
            .sorted(Comparator.comparingInt(this::getRemaining));

        return Stream.concat(existing, newOrders.stream())
            .collect(Collectors.toList());
    }

    private int getRemaining(ProductionOrder order) {
        return order.getTargetAmount() - order.getProducedAmount();
    }
}
