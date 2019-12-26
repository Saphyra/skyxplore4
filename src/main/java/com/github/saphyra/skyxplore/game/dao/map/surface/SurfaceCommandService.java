package com.github.saphyra.skyxplore.game.dao.map.surface;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SurfaceCommandService {
    private final SurfaceDao surfaceDao;

    public void save(Surface surface) {
        surfaceDao.save(surface);
    }
}
