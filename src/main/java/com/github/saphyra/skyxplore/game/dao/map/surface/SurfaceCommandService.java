package com.github.saphyra.skyxplore.game.dao.map.surface;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SurfaceCommandService {
    private final SurfaceDao surfaceDao;

    public void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        surfaceDao.deleteByGameIdAndUserId(gameId, userId);
    }

    public void save(Surface surface) {
        surfaceDao.save(surface);
    }

    public void saveAll(List<Surface> surfaces) {
        surfaceDao.saveAll(surfaces);
    }
}
