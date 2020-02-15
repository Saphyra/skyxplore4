package com.github.saphyra.skyxplore_deprecated.game.dao.map.connection;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore_deprecated.common.UuidConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
class StarConnectionDao extends AbstractDao<StarConnectionEntity, StarConnection, String, StarConnectionRepository> {
    private final UuidConverter uuidConverter;

    StarConnectionDao(StarConnectionConverter converter, StarConnectionRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameId(UUID gameId) {
        log.info("Deleting StarConnections for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    @Override
    public void saveAll(List<StarConnection> connections) {
        repository.saveAll(converter.convertDomain(connections));
    }

    List<StarConnection> getByGameIdAndUserId(UUID gameId) {
        return converter.convertEntity(
            repository.getByGameId(
                uuidConverter.convertDomain(gameId)
            )
        );
    }
}
