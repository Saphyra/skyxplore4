package com.github.saphyra.skyxplore.game.module.map.surface;

import com.github.saphyra.skyxplore.game.module.map.surface.domain.Surface;
import com.github.saphyra.skyxplore.game.module.map.surface.domain.SurfaceDao;
import com.github.saphyra.skyxplore.game.module.map.surface.domain.SurfaceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SurfaceQueryService {
    private final SurfaceDao surfaceDao;

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
}
