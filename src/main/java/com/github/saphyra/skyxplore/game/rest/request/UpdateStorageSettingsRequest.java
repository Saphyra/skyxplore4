package com.github.saphyra.skyxplore.game.rest.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateStorageSettingsRequest {
    @NotNull
    @Min(1)
    private Integer targetAmount;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer priority;
}
