package com.github.saphyra.skyxplore.game.star;

import com.github.saphyra.skyxplore.common.event.GameCreatedEvent;
import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.star.creation.StarCreationService;
import com.github.saphyra.skyxplore.game.star.domain.StarDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class StarService {
    private final StarCreationService starCreationService;
    private final StarDao starDao;

    @EventListener
    void gameCreatedEventListener(GameCreatedEvent event){
        starCreationService.createStars(event.getUserId(), event.getGameId());
    }

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event){
        starDao.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }
}
