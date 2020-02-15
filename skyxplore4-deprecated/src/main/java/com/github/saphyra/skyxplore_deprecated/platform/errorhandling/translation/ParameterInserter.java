package com.github.saphyra.skyxplore_deprecated.platform.errorhandling.translation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
class ParameterInserter {
    private final ParamKeyAssembler paramKeyAssembler;

    String insertParams(String errorMessage, Map<String, String> params) {
        log.debug("Inserting parameters {} to template {}", params, errorMessage);
        String result = errorMessage;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = paramKeyAssembler.assembleKey(entry.getKey());
            result = result.replaceAll(key, entry.getValue());
        }
        log.debug("Result: {}", result);
        return result;
    }
}
