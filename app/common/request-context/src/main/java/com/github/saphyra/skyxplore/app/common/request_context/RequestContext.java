package com.github.saphyra.skyxplore.app.common.request_context;

import static java.util.Objects.isNull;

import java.util.Optional;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestContext {
    private final UUID userId;
    private final UUID gameId;
    private final UUID playerId;

    public UUID getUserId() {
        return Optional.ofNullable(userId)
            .orElseThrow(() -> new IllegalStateException("userId is null."));
    }

    public UUID getGameId() {
        return Optional.ofNullable(gameId)
            .orElseThrow(() -> new IllegalStateException("gameId is null."));
    }

    public UUID getPlayerId() {
        return Optional.ofNullable(playerId)
            .orElseThrow(() -> new IllegalStateException("playerId is null."));
    }

    public boolean isUserIdPresent() {
        return !isNull(userId);
    }

    public boolean isGameIdPresent() {
        return !isNull(gameId);
    }

    public boolean isPlayerIdPresent() {
        return !isNull(playerId);
    }

    @Override
    public String toString() {
        return String.format("RequestContext - userId: %s, gameId: %s, playerId: %s", userId, gameId, playerId);
    }
}
