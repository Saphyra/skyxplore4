package com.github.saphyra.skyxplore.game.common;

import com.github.saphyra.skyxplore.game.rest.view.star.StarDetailsView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StarSystemDetailsQueryService {
    public StarDetailsView getDetailsOfStarSystem(UUID starId, UUID playerId){
        throw new UnsupportedOperationException(); //TODO implement
    }
}
