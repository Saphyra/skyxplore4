package com.github.saphyra.skyxplore.game.newround.hr;

import com.github.saphyra.skyxplore.game.dao.system.citizen.Citizen;
import org.springframework.stereotype.Component;

@Component
class HumanResourceConverter {
    HumanResource convert(Citizen citizen) {
        //TODO implement
        return HumanResource.builder()
            .build();
    }
}
