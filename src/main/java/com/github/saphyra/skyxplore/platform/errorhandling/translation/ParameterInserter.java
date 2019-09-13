package com.github.saphyra.skyxplore.platform.errorhandling.translation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
class ParameterInserter {
    private final ParamKeyAssembler paramKeyAssembler;

    String insertParams(String errorMessage, Map<String, String> params) {
        String result = errorMessage;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = paramKeyAssembler.assembleKey(entry.getKey());
            result = result.replaceAll(key, entry.getValue());
        }
        return result;
    }
}
