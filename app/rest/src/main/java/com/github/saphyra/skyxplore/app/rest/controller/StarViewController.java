package com.github.saphyra.skyxplore.app.rest.controller;

import static com.github.saphyra.skyxplore.app.common.config.RequestConstants.API_PREFIX;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.app.common.common_request.OneStringParamRequest;
import com.github.saphyra.skyxplore.app.service.star.RenameStarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
//TODO unit test
//TODO api test
class StarViewController {
    private static final String RENAME_STAR_MAPPING = API_PREFIX + "/game/star/{starId}";

    private final RenameStarService renameStarService;

    @PostMapping(RENAME_STAR_MAPPING)
    void renameStar(
        @PathVariable("starId") UUID starId,
        @RequestBody @Valid OneStringParamRequest newName
    ) {
        log.info("Renaming star {}", starId);
        renameStarService.rename(starId, newName.getValue());
    }
}
