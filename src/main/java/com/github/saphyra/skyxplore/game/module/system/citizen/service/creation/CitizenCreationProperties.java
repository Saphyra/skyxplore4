package com.github.saphyra.skyxplore.game.module.system.citizen.service.creation;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class CitizenCreationProperties {
    @Value("${com.github.saphyra.skyxplore.game.citizen.initialAmount}")
    private Integer initialAmount;
}
