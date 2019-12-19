package com.github.saphyra.skyxplore.game.dao.map.connection;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
class StarConnectionDao extends AbstractDao<StarConnectionEntity, StarConnection, String, StarConnectionRepository> {
    private final UuidConverter uuidConverter;

    StarConnectionDao(StarConnectionConverter converter, StarConnectionRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        );
    }

    void saveAll(List<StarConnection> connections) {
        repository.saveAll(converter.convertDomain(connections));
    }

    List<StarConnection> getByGameIdAndUserId(UUID gameId, UUID userId) {
        return converter.convertEntity(
            repository.getByGameIdAndUserId(
                uuidConverter.convertDomain(gameId),
                uuidConverter.convertDomain(userId)
            )
        );
    }
}
