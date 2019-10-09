package com.github.saphyra.skyxplore.game.map.surface.domain;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;

@Component
public class SurfaceDao extends AbstractDao<SurfaceEntity, Surface, String, SurfaceRepository> {
    private final UuidConverter uuidConverter;

    public SurfaceDao(Converter<SurfaceEntity, Surface> converter, SurfaceRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        );
    }
}
