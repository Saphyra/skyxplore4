package com.github.saphyra.skyxplore.game.dao.system.storage.reservation;

import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheRepository;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Primary
@Slf4j
public class CachingReservationRepository extends CacheRepository<String, ReservationEntity, String, ReservationRepository> implements ReservationRepository {
    private final RequestContextHolder requestContextHolder;
    private final UuidConverter uuidConverter;

    protected CachingReservationRepository(ReservationRepository repository, RequestContextHolder requestContextHolder, UuidConverter uuidConverter) {
        super(repository, ReservationEntity::getGameId);
        this.requestContextHolder = requestContextHolder;
        this.uuidConverter = uuidConverter;
    }

    @Override
    protected List<ReservationEntity> getByKey(String gameId) {
        List<ReservationEntity> entities = repository.getByGameId(gameId);
        log.info("ReservationEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<String> ids) {
        repository.deleteByReservationIdIn(ids);
    }

    @Override
    public void deleteByExternalReferenceAndPlayerId(String externalReference, String playerId) {
        getMap(getGameId())
            .values()
            .stream()
            .filter(reservationEntity -> reservationEntity.getExternalReference().equals(externalReference))
            .filter(reservationEntity -> reservationEntity.getPlayerId().equals(playerId))
            .forEach(this::delete);
    }

    @Override
    public void deleteByGameId(String gameId) {
        processDeletions();
        cacheMap.remove(gameId);
        repository.deleteByGameId(gameId);
    }

    @Override
    public void deleteByReservationIdIn(List<String> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public List<ReservationEntity> getByGameId(String gameId) {
        //noinspection SimplifyStreamApiCallChains
        return Optional.ofNullable(cacheMap.get(gameId))
            .map(map -> map.values().stream().collect(Collectors.toList()))
            .orElseGet(() -> addToCache(gameId, getByKey(gameId)));
    }

    @Override
    public List<ReservationEntity> getByStarIdAndStorageTypeAndPlayerId(String starId, StorageType storageType, String playerId) {
        return getMap(getGameId())
            .values()
            .stream()
            .filter(reservationEntity -> reservationEntity.getStarId().equals(starId))
            .filter(reservationEntity -> reservationEntity.getStorageType().equals(storageType))
            .filter(reservationEntity -> reservationEntity.getPlayerId().equals(playerId))
            .collect(Collectors.toList());
    }

    @Override
    public List<ReservationEntity> getByStarIdAndDataIdAndPlayerId(String starId, String dataId, String playerId) {
        return getMap(getGameId())
            .values()
            .stream()
            .filter(reservationEntity -> reservationEntity.getStarId().equals(starId))
            .filter(reservationEntity -> reservationEntity.getDataId().equals(dataId))
            .filter(reservationEntity -> reservationEntity.getPlayerId().equals(playerId))
            .collect(Collectors.toList());
    }

    private String getGameId() {
        return uuidConverter.convertDomain(requestContextHolder.get().getGameId());
    }
}
