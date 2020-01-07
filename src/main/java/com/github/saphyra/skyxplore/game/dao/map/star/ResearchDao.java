package com.github.saphyra.skyxplore.game.dao.map.star;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.common.interfaces.DeletableByGameId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class ResearchDao extends AbstractDao<ResearchEntity, Research, String, ResearchRepository> implements DeletableByGameId {
    private final UuidConverter uuidConverter;

    public ResearchDao(ResearchConverter converter, ResearchRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        log.info("Deleting researches for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    public List<Research> getByStarIdAndGameIdAndPlayerId(UUID starId, UUID gameId, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndGameIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        ));
    }
}