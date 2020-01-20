package com.github.saphyra.skyxplore.game.newround.state;

import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ProductionBuildingStateService implements StateService {
    private final ConcurrentHashMap<UUID, ConcurrentHashMap<UUID, ProductionBuildingState>> buildingStates = new ConcurrentHashMap<>();

    private final RequestContextHolder requestContextHolder;

    @Override
    public void clear() {
        buildingStates.remove(requestContextHolder.get().getGameId());
    }
}
