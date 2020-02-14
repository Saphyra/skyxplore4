package com.github.saphyra.skyxplore.game.rest.view.system;

import com.github.saphyra.skyxplore.game.dao.system.priority.PriorityType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class PriorityView {
    @NonNull
    private final PriorityType type;

    @NonNull
    private final Integer priority;
}
