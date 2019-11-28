package com.github.saphyra.skyxplore.game.rest.request;

import javax.validation.constraints.NotNull;

import com.github.saphyra.skyxplore.game.common.interfaces.QueueType;
import lombok.Data;

@Data
public class UpdatePriorityRequest {
    @NotNull
    private QueueType queueType;

    @NotNull
    private Integer priority;
}
