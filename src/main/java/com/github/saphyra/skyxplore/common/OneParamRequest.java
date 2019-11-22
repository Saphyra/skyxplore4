package com.github.saphyra.skyxplore.common;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneParamRequest<T> {
    @NotNull
    private T value;
}
