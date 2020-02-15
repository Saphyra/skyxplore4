package com.github.saphyra.skyxplore_deprecated.game.service.map.star;

import com.github.saphyra.skyxplore_deprecated.common.OneStringParamRequest;
import com.github.saphyra.skyxplore_deprecated.game.service.map.star.rename.RenameStarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.API_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StarController {
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
