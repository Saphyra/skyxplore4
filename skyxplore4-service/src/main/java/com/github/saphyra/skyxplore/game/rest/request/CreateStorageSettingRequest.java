package com.github.saphyra.skyxplore.game.rest.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreateStorageSettingRequest {
    @NotNull
    private String dataId;

    @NotNull
    @Min(1)
    private Integer targetAmount;

    @NotNull
    @Min(1)
    private Integer batchSize;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer priority;
}
