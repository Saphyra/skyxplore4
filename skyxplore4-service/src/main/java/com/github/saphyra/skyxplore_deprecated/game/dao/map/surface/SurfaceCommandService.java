package com.github.saphyra.skyxplore_deprecated.game.dao.map.surface;

import com.github.saphyra.skyxplore_deprecated.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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
