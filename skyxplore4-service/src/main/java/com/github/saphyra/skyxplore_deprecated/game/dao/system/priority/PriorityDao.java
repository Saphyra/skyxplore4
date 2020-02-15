package com.github.saphyra.skyxplore_deprecated.game.dao.system.priority;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore_deprecated.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
class PriorityDao extends AbstractDao<PriorityEntity, Priority, PriorityEntityId, PriorityRepository> {
    private final UuidConverter uuidConverter;

    public PriorityDao(PriorityConverter converter, PriorityRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameId(UUID gameId) {
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    public List<Priority> getByStarIdAndPlayerId(UUID starId, UUID playerId) {
        return converter.convertEntity(repository.getByIdStarIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    Optional<Priority> findByStarIdAndPriorityTypeAndPlayerId(UUID starId, PriorityType type, UUID playerId) {
        return converter.convertEntity(repository.findByIdAndPlayerId(
            new PriorityEntityId(
                uuidConverter.convertDomain(starId),
                type
            ),
            uuidConverter.convertDomain(playerId)
        ));
    }
}
