package com.github.saphyra.skyxplore.game.dao.system.storage.allocation;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;

@Component
public class AllocationDao extends AbstractDao<AllocationEntity, Allocation, String, AllocationRepository> {
    private final UuidConverter uuidConverter;

    public AllocationDao(AllocationConverter converter, AllocationRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        );
    }
}
