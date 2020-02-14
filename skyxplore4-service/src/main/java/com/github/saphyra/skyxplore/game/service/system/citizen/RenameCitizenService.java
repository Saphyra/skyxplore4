package com.github.saphyra.skyxplore.game.service.system.citizen;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.game.dao.system.citizen.Citizen;
import com.github.saphyra.skyxplore.game.dao.system.citizen.CitizenCommandService;
import com.github.saphyra.skyxplore.game.dao.system.citizen.CitizenQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
class RenameCitizenService {
    private final CitizenQueryService citizenQueryService;
    private final CitizenCommandService citizenCommandService;

    void rename(UUID citizenId, String newName) {
        validate(newName);
        Citizen citizen = citizenQueryService.findByCitizenIdAndOwnerId(citizenId);
        citizen.setCitizenName(newName);
        citizenCommandService.save(citizen);
    }

    private void validate(String newName) {
        if (newName.length() < 3 || newName.length() > 30) {
            throw ExceptionFactory.invalidCitizenName(newName);
        }
    }
}
