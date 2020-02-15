package com.github.saphyra.skyxplore_deprecated.platform.errorhandling.translation;

import org.springframework.stereotype.Component;

@Component
class ParamKeyAssembler {
    String assembleKey(String key) {
        return "\\$\\{" + key + "\\}";
    }
}
