package com.github.saphyra.skyxplore_deprecated.game.service.system.citizen.creation;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
class CitizenCreationProperties {
    @Value("${com.github.saphyra.skyxplore.game.citizen.initialAmount}")
    private Integer initialAmount;
}
