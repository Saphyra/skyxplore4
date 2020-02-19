package com.github.saphyra.skyxplore.common.exception_handling.localization;

import org.springframework.stereotype.Component;

@Component
//TODO unit test
class ParamKeyAssembler {
    String assembleKey(String key) {
        return "\\$\\{" + key + "\\}";
    }
}
