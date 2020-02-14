package com.github.saphyra.skyxplore.game.service.system.citizen;

import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
class CitizenController {
    private static final String RENAME_CITIZEN_MAPPING = API_PREFIX + "/game/star/citizen/{citizenId}";

    private final RenameCitizenService renameCitizenService;

    @PostMapping(RENAME_CITIZEN_MAPPING)
    void renameCitizen(
        @PathVariable("citizenId") UUID citizenId,
        @RequestBody OneStringParamRequest newName
    ) {
        log.info("Renaming citizen {} to {}", citizenId, newName);
        renameCitizenService.rename(citizenId, newName.getValue());
    }
}
