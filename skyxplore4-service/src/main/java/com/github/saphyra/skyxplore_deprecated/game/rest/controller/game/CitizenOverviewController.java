package com.github.saphyra.skyxplore_deprecated.game.rest.controller.game;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.citizen.CitizenQueryService;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.system.CitizenView;
import com.github.saphyra.skyxplore_deprecated.game.rest.view.system.CitizenViewConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.API_PREFIX;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CitizenOverviewController {
    private static final String GET_CITIZEN_OVERVIEW_MAPPING = API_PREFIX + "/game/star/{starId}/population/overview";

    private final CitizenQueryService citizenQueryService;
    private final CitizenViewConverter citizenViewConverter;

    @GetMapping(GET_CITIZEN_OVERVIEW_MAPPING)
    List<CitizenView> getCitizensOfStar(@PathVariable("starId") UUID starId) {
        return citizenViewConverter.convertDomain(citizenQueryService.getByLocationIdAndOwnerId(starId));
    }
}
