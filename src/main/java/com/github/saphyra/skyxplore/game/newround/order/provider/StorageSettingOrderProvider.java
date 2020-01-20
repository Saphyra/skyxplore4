package com.github.saphyra.skyxplore.game.newround.order.provider;

import com.github.saphyra.skyxplore.game.dao.system.priority.Priority;
import com.github.saphyra.skyxplore.game.dao.system.priority.PriorityQueryService;
import com.github.saphyra.skyxplore.game.dao.system.priority.PriorityType;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSetting;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSettingQueryService;
import com.github.saphyra.skyxplore.game.newround.order.Order;
import com.github.saphyra.skyxplore.game.newround.order.StorageSettingOrder;
import com.github.saphyra.skyxplore.game.newround.order.processor.StorageSettingOrderProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class StorageSettingOrderProvider implements OrderProvider {
    private final PriorityQueryService priorityQueryService;
    private final ResourceQueryService resourceQueryService;
    private final StorageSettingOrderProcessor storageSettingOrderProcessor;
    private final StorageSettingQueryService storageSettingQueryService;

    @Override
    public List<Order> getForStar(UUID starId) {
        Map<PriorityType, Integer> priorities = priorityQueryService.getByStarIdAndPlayerId(starId)
            .stream()
            .collect(Collectors.toMap(Priority::getType, Priority::getValue));
        return storageSettingQueryService.getByStarIdAndPlayerId(starId)
            .stream()
            .map(storageSetting -> convert(storageSetting, priorities))
            .filter(storageSettingOrder -> storageSettingOrder.getMissingAmount() > 0)
            .collect(Collectors.toList());
    }

    private StorageSettingOrder convert(StorageSetting storageSetting, Map<PriorityType, Integer> priorities) {
        Integer currentAmount = resourceQueryService.findLatestAmountByStarIdAndDataIdAndPlayerId(storageSetting.getStarId(), storageSetting.getDataId());
        return StorageSettingOrder.builder()
            .storageSetting(storageSetting)
            .orderProcessor(storageSettingOrderProcessor)
            .priority(storageSetting.getPriority() * priorities.get(storageSetting.getPriorityType()))
            .missingAmount(currentAmount - storageSetting.getTargetAmount())
            .build();
    }
}
