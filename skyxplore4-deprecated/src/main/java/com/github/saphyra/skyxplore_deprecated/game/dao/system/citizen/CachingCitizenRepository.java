package com.github.saphyra.skyxplore_deprecated.game.dao.system.citizen;

import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore_deprecated.game.dao.common.cache.CacheContext;
import com.github.saphyra.skyxplore_deprecated.game.dao.common.cache.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Primary
@Slf4j
@ConditionalOnProperty(value = "com.github.saphyra.skyxplore.cacheRepository.enabled", havingValue = "true")
public class CachingCitizenRepository extends CacheRepository<String, CitizenEntity, String, CitizenRepository> implements CitizenRepository {
    private final RequestContextHolder requestContextHolder;
    private final UuidConverter uuidConverter;

    protected CachingCitizenRepository(CitizenRepository repository, RequestContextHolder requestContextHolder, UuidConverter uuidConverter, CacheContext cacheContext) {
        super(repository, CitizenEntity::getGameId, cacheContext);
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
    public Optional<CitizenEntity> findByCitizenIdAndOwnerId(String citizenId, String ownerId) {
        return getMapByKey(getGameId())
            .values()
            .stream()
            .filter(citizenEntity -> citizenEntity.getCitizenId().equals(citizenId))
            .filter(citizenEntity -> citizenEntity.getOwnerId().equals(ownerId))
            .findAny();
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
