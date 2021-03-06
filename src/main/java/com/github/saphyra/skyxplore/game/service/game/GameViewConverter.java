package com.github.saphyra.skyxplore.game.service.game;

import com.github.saphyra.skyxplore.common.ViewConverter;
import com.github.saphyra.skyxplore.game.dao.game.Game;
import com.github.saphyra.skyxplore.game.rest.view.GameView;
import org.springframework.stereotype.Component;

@Component
public class GameViewConverter implements ViewConverter<Game, GameView> {
    @Override
    public GameView convertDomain(Game domain) {
        return new GameView(domain.getGameId(), domain.getGameName());
    }
}
