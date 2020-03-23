package com.github.saphyra.skyxplore.app.domain.surface;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.game_context.CommandService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SurfaceCommandService implements CommandService<Surface> {
    private final SurfaceDao surfaceDao;

    @Override
    public void delete(Surface domain) {
        surfaceDao.delete(domain);
    }

    @Override
    public void deleteAll(List<Surface> domains) {
        surfaceDao.deleteAll(domains);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        surfaceDao.deleteByGameId(gameId);
    }

    @Override
    public void save(Surface surface) {
        surfaceDao.save(surface);
    }

    @Override
    public void saveAll(List<Surface> domains) {
        surfaceDao.saveAll(domains);
    }

    @Override
    public Class<Surface> getType() {
        return Surface.class;
    }
}
