package com.github.saphyra.skyxplore_deprecated.game.newround.hr;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class HumanResourceProperties {
    @Value("${com.github.saphyra.skyxplore.game.humanResource.workPoints}")
    private Integer workPoints;
}
