package com.github.saphyra.skyxplore.app.rest.controller;

import static com.github.saphyra.skyxplore.app.common.config.RequestConstants.API_PREFIX;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.app.common.common_request.OneStringParamRequest;
import com.github.saphyra.skyxplore.app.domain.star.StarQueryService;
import com.github.saphyra.skyxplore.app.rest.view.map.StarMapView;
import com.github.saphyra.skyxplore.app.rest.view.map.StarMapViewConverter;
import com.github.saphyra.skyxplore.app.service.star.RenameStarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
class StarViewController {
    private static final String GET_STAR_MAPPING = API_PREFIX + "/game/star/{starId}";
    private static final String RENAME_STAR_MAPPING = API_PREFIX + "/game/star/{starId}";

    private final RenameStarService renameStarService;
    private final StarMapViewConverter starMapViewConverter;
    private final StarQueryService starQueryService;

    @GetMapping(GET_STAR_MAPPING)
    StarMapView getStar(
        @PathVariable("starId") UUID starId
    ) {
        return starMapViewConverter.convertDomain(starQueryService.findByStarIdAndOwnerId(starId));
    }

    @PostMapping(RENAME_STAR_MAPPING)
    void renameStar(
        @PathVariable("starId") UUID starId,
        @RequestBody @Valid OneStringParamRequest newName
    ) {
        log.info("Renaming star {}", starId);
        renameStarService.rename(starId, newName.getValue());
    }
}
