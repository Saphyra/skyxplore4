package com.github.saphyra.skyxplore.game.module.map.star.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;

@Component
public class StarDao extends AbstractDao<StarEntity, Star, String, StarRepository> {
    private final UuidConverter uuidConverter;

    public StarDao(StarConverter converter, StarRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        );
    }

    public List<Star> getByGameIdAndUserId(UUID gameId, UUID userId) {
        return converter.convertEntity(repository.getByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        ));
    }

    public void saveAll(List<Star> createdStars) {
        repository.saveAll(converter.convertDomain(createdStars));
    }
}
