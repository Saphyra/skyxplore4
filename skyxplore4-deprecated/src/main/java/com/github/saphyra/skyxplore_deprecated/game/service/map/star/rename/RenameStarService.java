package com.github.saphyra.skyxplore_deprecated.game.service.map.star.rename;

import com.github.saphyra.skyxplore_deprecated.common.ExceptionFactory;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.star.Star;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.star.StarCommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.star.StarQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RenameStarService {
    private final StarCommandService starCommandService;
    private final StarQueryService starQueryService;

    public void rename(UUID starId, String newName) {
        validate(newName);
        Star star = starQueryService.findByStarIdAndOwnerId(starId);
        star.setStarName(newName);
        starCommandService.save(star);
    }

    private void validate(String newName) {
        if (newName.length() < 3 || newName.length() > 30) {
            throw ExceptionFactory.invalidStarName();
        }
    }
}
