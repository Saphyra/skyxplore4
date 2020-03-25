package com.github.saphyra.skyxplore.app.game_data.domain.research;

import lombok.Data;

@Data
public class Unlock {
    private UnlockType type;
    private String dataId;
}
