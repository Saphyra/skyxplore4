package com.github.saphyra.skyxplore.test.framework.response;

import lombok.Data;

import java.util.UUID;

@Data
public class GameViewResponse {
    private UUID gameId;
    private String gameName;
}
