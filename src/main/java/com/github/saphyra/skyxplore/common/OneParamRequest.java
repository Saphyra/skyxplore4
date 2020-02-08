package com.github.saphyra.skyxplore.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneParamRequest<T> {
    @NotNull
    private T value;
}
