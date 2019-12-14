package com.github.saphyra.skyxplore.common.context;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestContext {
    private final String userId;
    private final String gameId;
    private final String playerId;
}
