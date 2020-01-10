package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Primary
@Slf4j
public class CachingResourceRepository extends CacheRepository<String, ResourceEntity, String, ResourceRepository> implements ResourceRepository {
    private final RequestContextHolder requestContextHolder;
    private final UuidConverter uuidConverter;

    protected CachingResourceRepository(ResourceRepository repository, RequestContextHolder requestContextHolder, UuidConverter uuidConverter) {
        super(repository, ResourceEntity::getGameId);
        this.requestContextHolder = requestContextHolder;
        this.uuidConverter = uuidConverter;
    }

    @Override
    protected List<ResourceEntity> getByKey(String gameId) {
        List<ResourceEntity> entities = repository.getByGameId(gameId);
        log.info("ResourceEntit" +
            "ies loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<String> ids) {
        repository.deleteByResourceIdIn(ids);
    }

    @Override
    public void deleteByGameId(String gameId) {
        processDeletions();
        cacheMap.remove(gameId);
        repository.deleteByGameId(gameId);
    }

    @Override
    public void deleteByResourceIdIn(List<String> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public Optional<ResourceEntity> findByStarIdAndDataIdAndRoundAndPlayerId(String starId, String dataId, int round, String playerId) {
        return getMap(getGameId())
            .values()
            .stream()
            .filter(resourceEntity -> resourceEntity.getStarId().equals(starId))
            .filter(resourceEntity -> resourceEntity.getDataId().equals(dataId))
            .filter(resourceEntity -> resourceEntity.getRound().equals(round))
            .filter(resourceEntity -> resourceEntity.getPlayerId().equals(playerId))
            .findAny();
    }

    @Override
    public Optional<ResourceEntity> findLatestByStarIdAndDataIdAndPlayerIdOrderByRoundDesc(String starId, String dataId, String playerId) {
        return getMap(getGameId())
            .values()
            .stream()
            .filter(resourceEntity -> resourceEntity.getStarId().equals(starId))
            .filter(resourceEntity -> resourceEntity.getDataId().equals(dataId))
            .filter(resourceEntity -> resourceEntity.getPlayerId().equals(playerId))
            .sorted((o1, o2) -> o2.getRound() - o1.getRound())
            .findAny();
    }

    @Override
    public List<ResourceEntity> getByGameId(String gameId) {
        //noinspection SimplifyStreamApiCallChains
        return Optional.ofNullable(cacheMap.get(gameId))
            .map(map -> map.values().stream().collect(Collectors.toList()))
            .orElseGet(() -> addToCache(gameId, getByKey(gameId)));
    }

    @Override
    public List<ResourceEntity> getByStarIdAndStorageTypeAndPlayerId(String starId, StorageType storageType, String playerId) {
        return getMap(getGameId())
            .values()
            .stream()
            .filter(resourceEntity -> resourceEntity.getStarId().equals(starId))
            .filter(resourceEntity -> resourceEntity.getStorageType().equals(storageType))
            .filter(resourceEntity -> resourceEntity.getPlayerId().equals(playerId))
            .collect(Collectors.toList());
    }

    @Override
    public List<ResourceEntity> getByStarIdAndDataIdAndPlayerId(String starId, String dataId, String playerId) {
        return getMap(getGameId())
            .values()
            .stream()
            .filter(resourceEntity -> resourceEntity.getStarId().equals(starId))
            .filter(resourceEntity -> resourceEntity.getDataId().equals(dataId))
            .filter(resourceEntity -> resourceEntity.getPlayerId().equals(playerId))
            .collect(Collectors.toList());
    }

    private String getGameId() {
        return uuidConverter.convertDomain(requestContextHolder.get().getGameId());
    }
}
