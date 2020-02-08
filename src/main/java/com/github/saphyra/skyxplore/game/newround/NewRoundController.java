package com.github.saphyra.skyxplore.game.newround;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.dao.game.Game;
import com.github.saphyra.skyxplore.game.dao.game.GameCommandService;
import com.github.saphyra.skyxplore.game.dao.game.GameQueryService;
import com.github.saphyra.skyxplore.game.dao.map.star.Star;
import com.github.saphyra.skyxplore.game.dao.map.star.StarQueryService;
import com.github.saphyra.skyxplore.game.dao.player.Player;
import com.github.saphyra.skyxplore.game.dao.player.PlayerQueryService;
import com.github.saphyra.skyxplore.game.newround.hr.HumanResourceService;
import com.github.saphyra.skyxplore.game.newround.order.Order;
import com.github.saphyra.skyxplore.game.newround.order.provider.OrderProvider;
import com.github.saphyra.skyxplore.game.newround.resource.NewRoundResourceHandler;
import com.github.saphyra.skyxplore.game.newround.resource.StorageSettingReservationUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
//TODO refactor - split
public class NewRoundController {
    private static final String NEW_ROUND_MAPPING = API_PREFIX + "/game";

    private final GameCommandService gameCommandService;
    private final GameQueryService gameQueryService;
    private final HumanResourceService humanResourceService;
    private final NewRoundResourceHandler newRoundResourceHandler;
    private final List<OrderProvider> orderProviders;
    private final PlayerQueryService playerQueryService;
    private final RequestContextHolder requestContextHolder;
    private final StarQueryService starQueryService;
    private final StorageSettingReservationUpdateService storageSettingReservationUpdateService;

    @PostMapping(NEW_ROUND_MAPPING)
    void newRound() {
        log.info("Processing new rounds...");
        Game game = gameQueryService.findByGameIdAndUserIdValidated();
        List<Player> players = playerQueryService.getByGameId();

        RequestContext context = requestContextHolder.get();
        players.stream()
            //.parallel()
            .forEach(player -> processNewRoundForPlayer(player, context.getUserId()));

        newRoundResourceHandler.cleanUpResources(context.getGameId());
        newRoundResourceHandler.prepareForNewRound(game.getRound());
        newRoundResourceHandler.deleteExpiredResources(game.getRound() + 1);
        humanResourceService.clear(game.getGameId());
        requestContextHolder.setContext(context);

        game.incrementRound();
        gameCommandService.save(game);
        log.info("New rounds finished.");
    }

    private void processNewRoundForPlayer(Player player, UUID userId) {
        try {
            RequestContext context = RequestContext.builder()
                .gameId(player.getGameId())
                .playerId(player.getPlayerId())
                .userId(userId)
                .build();
            requestContextHolder.setContext(context);

            starQueryService.getByOwnerId(player.getPlayerId())
                .stream()
                //.parallel()
                .forEach(this::processNewRoundForStar);
        } finally {
            requestContextHolder.clear();
        }
    }

    private void processNewRoundForStar(Star star) {
        List<Order> orders = orderProviders.stream()
            .flatMap(orderProvider -> orderProvider.getForStar(star.getStarId()).stream())
            .sorted(Comparator.comparingInt(Order::getPriority))
            .collect(Collectors.toList());
        log.info("Orders for star {}: {}", star.getStarId(), orders);
        orders.forEach(Order::process);
        storageSettingReservationUpdateService.updateForStar(star);
    }
}
