package com.github.saphyra.skyxplore.game.dao.map.connection;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.common.interfaces.DeletableByGameId;
import com.github.saphyra.skyxplore.game.common.interfaces.SaveAllDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
class StarConnectionDao extends AbstractDao<StarConnectionEntity, StarConnection, String, StarConnectionRepository> implements DeletableByGameId, SaveAllDao<StarConnection> {
    private final UuidConverter uuidConverter;

    StarConnectionDao(StarConnectionConverter converter, StarConnectionRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        log.info("Deleting StarConnections for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    @Override
    public void saveAll(List<StarConnection> connections) {
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
