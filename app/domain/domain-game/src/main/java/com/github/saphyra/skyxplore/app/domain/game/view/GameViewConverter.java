package com.github.saphyra.skyxplore.app.domain.game.view;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.common_request.ViewConverter;
import com.github.saphyra.skyxplore.app.domain.game.domain.Game;

@Component
public class GameViewConverter implements ViewConverter<Game, GameView> {
    @Override
    public GameView convertDomain(Game domain) {
        return new GameView(domain.getGameId(), domain.getGameName());
    }
}
