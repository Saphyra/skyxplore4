package com.github.saphyra.skyxplore.game.dao.system.storage.allocation;

import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheRepository;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
@Slf4j
public class CachingAllocationRepository extends CacheRepository<String, AllocationEntity, String, AllocationRepository> implements AllocationRepository {
    private final RequestContextHolder requestContextHolder;
    private final UuidConverter uuidConverter;

    protected CachingAllocationRepository(AllocationRepository repository, RequestContextHolder requestContextHolder, UuidConverter uuidConverter) {
        super(repository, AllocationEntity::getGameId);
        this.requestContextHolder = requestContextHolder;
        this.uuidConverter = uuidConverter;
    }

    @Override
    protected List<AllocationEntity> getByKey(String gameId) {
        List<AllocationEntity> entities = repository.getByGameId(gameId);
        log.info("AllocationEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<String> ids) {
        repository.deleteByAllocationIdIn(ids);
    }

    @Override
    public void deleteByAllocationIdIn(List<String> allocationIds) {
        allocationIds.forEach(this::deleteById);
    }

    @Override
    public void deleteByExternalReferenceAndPlayerId(String externalReference, String playerId) {
        deleteAll(getByExternalReferenceAndPlayerId(externalReference, playerId));
    }

    @Override
    public void deleteByGameId(String gameId) {
        processDeletions();
        deleteByKey(gameId);
        repository.deleteByGameId(gameId);
    }

    @Override
    public List<AllocationEntity> getByExternalReferenceAndPlayerId(String externalReference, String playerId) {
        return getMapByKey(getGameId())
            .values()
            .stream()
            .filter(allocationEntity -> allocationEntity.getExternalReference().equals(externalReference))
            .filter(allocationEntity -> allocationEntity.getPlayerId().equals(playerId))
            .collect(Collectors.toList());
    }

    @Override
    public List<AllocationEntity> getByGameId(String gameId) {
        return new ArrayList<>(getMapByKey(gameId).values());
    }

    @Override
    public List<AllocationEntity> getByStarIdAndStorageTypeAndPlayerId(String starId, StorageType storageType, String playerId) {
        return getMapByKey(getGameId())
            .values()
            .stream()
            .filter(allocationEntity -> allocationEntity.getStarId().equals(starId))
            .filter(allocationEntity -> allocationEntity.getStorageType().equals(storageType))
            .filter(allocationEntity -> allocationEntity.getPlayerId().equals(playerId))
            .collect(Collectors.toList());
    }

    @Override
    public List<AllocationEntity> getByStarIdAndDataIdAndPlayerId(String starId, String dataId, String playerId) {
        return getMapByKey(getGameId())
            .values()
            .stream()
            .filter(allocationEntity -> allocationEntity.getStarId().equals(starId))
            .filter(allocationEntity -> allocationEntity.getDataId().equals(dataId))
            .filter(allocationEntity -> allocationEntity.getPlayerId().equals(playerId))
            .collect(Collectors.toList());
    }

    private String getGameId() {
        return uuidConverter.convertDomain(requestContextHolder.get().getGameId());
    }
}
