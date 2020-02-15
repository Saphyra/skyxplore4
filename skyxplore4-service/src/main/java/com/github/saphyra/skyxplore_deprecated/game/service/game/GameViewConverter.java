package com.github.saphyra.skyxplore_deprecated.game.service.game;

import com.github.saphyra.skyxplore_deprecated.common.ViewConverter;
import com.github.saphyra.skyxplore_deprecated.game.dao.game.Game;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.GameView;
import org.springframework.stereotype.Component;

@Component
public class GameViewConverter implements ViewConverter<Game, GameView> {
    @Override
    public GameView convertDomain(Game domain) {
        return new GameView(domain.getGameId(), domain.getGameName());
    }
}
