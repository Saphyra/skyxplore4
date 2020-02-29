package com.github.saphyra.skyxplore.app.common.common_request;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OneStringParamRequest extends OneParamRequest<String> {
    public OneStringParamRequest(String value) {
        super(value);
    }
}
