package com.github.saphyra.skyxplore.app.service.star;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.app.common.exception_handling.ExceptionFactory;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star.StarCommandService;
import com.github.saphyra.skyxplore.app.domain.star.StarQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
