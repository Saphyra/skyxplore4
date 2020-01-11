package com.github.saphyra.skyxplore.game.dao.map.star;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
class StarDao extends AbstractDao<StarEntity, Star, String, StarRepository> {
    private final UuidConverter uuidConverter;

    StarDao(StarConverter converter, StarRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameId(UUID gameId) {
        log.info("Deleting stars for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    Optional<Star> findByStarIdAndOwnerId(UUID starId, UUID playerId) {
        return converter.convertEntity(repository.findByStarIdAndOwnerId(
            uuidConverter.convertDomain(starId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    List<Star> getByOwnerId(UUID ownerId) {
        return converter.convertEntity(repository.getByOwnerId(
            uuidConverter.convertDomain(ownerId)
        ));
    }

    @Override
    public void saveAll(List<Star> createdStars) {
        repository.saveAll(converter.convertDomain(createdStars));
    }
}
