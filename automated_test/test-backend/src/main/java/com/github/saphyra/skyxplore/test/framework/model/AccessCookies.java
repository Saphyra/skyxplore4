package com.github.saphyra.skyxplore.test.framework.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessCookies {
    private final String accessTokenId;
    private final String userId;
    private String gameId;
    private String playerId;
}
