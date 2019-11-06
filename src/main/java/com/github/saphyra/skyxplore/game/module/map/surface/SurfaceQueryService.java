package com.github.saphyra.skyxplore.game.module.map.surface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.module.map.surface.domain.Surface;
import com.github.saphyra.skyxplore.game.module.map.surface.domain.SurfaceDao;
import com.github.saphyra.skyxplore.game.module.map.surface.domain.SurfaceType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurfaceQueryService {
    private final SurfaceDao surfaceDao;
    private final UuidConverter uuidConverter;

    public Map<SurfaceType, List<Surface>> getMappingBySurfaceType(UUID starId) {
        List<Surface> surfaces = surfaceDao.getByStarId(starId);
        Map<SurfaceType, List<Surface>> result = new HashMap<>();
        for (Surface surface : surfaces) {
            if (!result.containsKey(surface.getSurfaceType())) {
                result.put(surface.getSurfaceType(), new ArrayList<>());
            }
            result.get(surface.getSurfaceType()).add(surface);
        }
        return result;
    }

    public Surface findBySurfaceId(UUID surfaceId) {
        return surfaceDao.findById(uuidConverter.convertDomain(surfaceId))
            .orElseThrow(() -> ExceptionFactory.surfaceNotFound(surfaceId));
    }
}
