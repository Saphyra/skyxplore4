package com.github.saphyra.skyxplore.game.service.map.surface;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceDao;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurfaceQueryService {
    private final SurfaceDao surfaceDao;
    private final UuidConverter uuidConverter;

    public Map<SurfaceType, List<Surface>> getMappingBySurfaceType(UUID starId) {
        return surfaceDao.getByStarId(starId).stream()
            .collect(Collectors.groupingBy(Surface::getSurfaceType));
    }

    public Surface findBySurfaceId(UUID surfaceId) {
        return surfaceDao.findById(uuidConverter.convertDomain(surfaceId))
            .orElseThrow(() -> ExceptionFactory.surfaceNotFound(surfaceId));
    }
}
