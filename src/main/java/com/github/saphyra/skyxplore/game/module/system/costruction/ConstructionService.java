package com.github.saphyra.skyxplore.game.module.system.costruction;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConstructionService {
    private final ConstructionDao constructionDao;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent gameDeletedEvent){
        constructionDao.deleteByGameIdAndUserId(gameDeletedEvent.getGameId(), gameDeletedEvent.getUserId());
    }
}
