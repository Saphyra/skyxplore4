package com.github.saphyra.skyxplore.game.dao.system.citizen;

import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
@Slf4j
public class CachingCitizenRepository extends CacheRepository<String, CitizenEntity, String, CitizenRepository> implements CitizenRepository {
    private final RequestContextHolder requestContextHolder;
    private final UuidConverter uuidConverter;

    protected CachingCitizenRepository(CitizenRepository repository, RequestContextHolder requestContextHolder, UuidConverter uuidConverter) {
        super(repository, CitizenEntity::getGameId);
        this.requestContextHolder = requestContextHolder;
        this.uuidConverter = uuidConverter;
    }

    @Override
    protected List<CitizenEntity> getByKey(String gameId) {
        List<CitizenEntity> entities = repository.getByGameId(gameId);
        log.info("CitizenEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<String> citizenIds) {
        repository.deleteByCitizenIdIn(citizenIds);
    }

    @Override
    public Integer countByLocationTypeAndLocationIdAndOwnerId(LocationType locationType, String locationId, String ownerId) {
        long count = getMapByKey(getGameId())
            .values()
            .stream()
            .filter(citizenEntity -> citizenEntity.getLocationType().equals(locationType))
            .filter(citizenEntity -> citizenEntity.getLocationId().equals(locationId))
            .filter(citizenEntity -> citizenEntity.getOwnerId().equals(ownerId))
            .count();
        return (int) count;
    }

    @Override
    public void deleteByCitizenIdIn(List<String> citizenIds) {
        citizenIds.forEach(this::deleteById);
    }

    @Override
    public void deleteByGameId(String gameId) {
        deleteByKey(gameId);
    }

    @Override
    public List<CitizenEntity> getByGameId(String gameId) {
        return new ArrayList<>(getMapByKey(gameId).values());
    }

    @Override
    public List<CitizenEntity> getByLocationIdAndOwnerId(String locationId, String ownerId) {
        return getMapByKey(getGameId())
            .values()
            .stream()
            .filter(citizenEntity -> citizenEntity.getLocationId().equals(locationId))
            .filter(citizenEntity -> citizenEntity.getOwnerId().equals(ownerId))
            .collect(Collectors.toList());
    }

    private String getGameId() {
        return uuidConverter.convertDomain(requestContextHolder.get().getGameId());
    }
}
