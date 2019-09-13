package com.github.saphyra.skyxplore.platform.errorhandling.translation;

import org.springframework.stereotype.Component;

@Component
class ParamKeyAssembler {
    String assembleKey(String key) {
        return "\\$\\{" + key + "\\}";
    }
}
