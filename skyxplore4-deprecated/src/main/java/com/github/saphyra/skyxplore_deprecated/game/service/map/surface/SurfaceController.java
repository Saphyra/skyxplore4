package com.github.saphyra.skyxplore_deprecated.game.service.map.surface;

import com.github.saphyra.skyxplore_deprecated.common.OneParamRequest;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.SurfaceType;
import com.github.saphyra.skyxplore_deprecated.game.service.map.surface.terraform.TerraformSurfaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.API_PREFIX;
import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.COOKIE_GAME_ID;
import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.COOKIE_PLAYER_ID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SurfaceController {
    private static final String TERRAFORM_SURFACE_MAPPING = API_PREFIX + "/game/surface/{surfaceId}/terraform";

    private final TerraformSurfaceService terraformSurfaceService;

    @PostMapping(TERRAFORM_SURFACE_MAPPING)
    void terraformSurface(
        @PathVariable("surfaceId") UUID surfaceId,
        @CookieValue(COOKIE_GAME_ID) UUID gameId,
        @CookieValue(COOKIE_PLAYER_ID) UUID playerId,
        @RequestBody OneParamRequest<SurfaceType> surfaceType
    ) {
        log.info("{} wants to terraform surface {} to {}", playerId, surfaceId, surfaceId);
        terraformSurfaceService.terraform(gameId, surfaceId, surfaceType.getValue());
    }
}
