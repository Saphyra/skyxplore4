package com.github.saphyra.skyxplore.app.domain.research;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
//TODO unit test
public class ResearchDao extends AbstractDao<ResearchEntity, Research, String, ResearchRepository> {
    private final UuidConverter uuidConverter;

    public ResearchDao(ResearchConverter converter, ResearchRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameId(UUID gameId) {
        log.info("Deleting researches for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    List<Research> getByStarIdAndPlayerId(UUID starId, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            uuidConverter.convertDomain(playerId)
        ));
    }
}
