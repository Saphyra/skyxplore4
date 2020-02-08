package com.github.saphyra.skyxplore.game.rest.request;

import com.github.saphyra.skyxplore.game.common.interfaces.QueueType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdatePriorityRequest {
    @NotNull
    private QueueType queueType;

    @NotNull
    private Integer priority;
}
