package com.github.saphyra.skyxplore.game.rest.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateStorageSettingRequest {
    @NotNull
    private String dataId;

    @NotNull
    @Size(min = 1)
    private Integer targetAmount;

    @NotNull
    @Size(min = 1, max = 10)
    private Integer priority;
}
