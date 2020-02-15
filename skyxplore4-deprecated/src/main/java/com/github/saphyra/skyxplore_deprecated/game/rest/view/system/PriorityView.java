package com.github.saphyra.skyxplore_deprecated.game.rest.view.system;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.priority.PriorityType;
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
