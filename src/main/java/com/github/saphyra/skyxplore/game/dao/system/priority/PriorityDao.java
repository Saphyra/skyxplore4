package com.github.saphyra.skyxplore.game.dao.system.priority;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PriorityDao extends AbstractDao<PriorityEntity, Priority, PriorityEntityId, PriorityRepository> {
    private final UuidConverter uuidConverter;
    public PriorityDao(PriorityConverter converter, PriorityRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameId(UUID gameId) {
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }
}
