package com.github.saphyra.skyxplore.game.dao.map.star;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
class StarDao extends AbstractDao<StarEntity, Star, String, StarRepository> {
    private final UuidConverter uuidConverter;

    StarDao(StarConverter converter, StarRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        );
    }

    Optional<Star> findByStarIdAndGameIdAndOwnerId(UUID starId, UUID gameId, UUID playerId) {
        return converter.convertEntity(repository.findByStarIdAndGameIdAndOwnerId(
            uuidConverter.convertDomain(starId),
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    List<Star> getByGameIdAndPlayerId(UUID gameId, UUID ownerId) {
        return converter.convertEntity(repository.getByGameIdAndOwnerId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(ownerId)
        ));
    }

    void saveAll(List<Star> createdStars) {
        repository.saveAll(converter.convertDomain(createdStars));
    }
}
