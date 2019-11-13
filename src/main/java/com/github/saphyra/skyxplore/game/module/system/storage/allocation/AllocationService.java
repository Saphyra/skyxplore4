package com.github.saphyra.skyxplore.game.module.system.storage.allocation;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.module.system.storage.allocation.domain.AllocationDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AllocationService {
    private final AllocationDao allocationDao;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event){
        allocationDao.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }
}
