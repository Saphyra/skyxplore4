package com.github.saphyra.skyxplore.app.common.exception_handling.localization;

import org.springframework.stereotype.Component;

@Component
class ParamKeyAssembler {
    String assembleKey(String key) {
        return String.format("${%s}", key);
    }
}
