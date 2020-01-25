package com.github.saphyra.skyxplore.game.dao.system.order.production;

import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheContext;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
@Slf4j
@ConditionalOnProperty(value = "com.github.saphyra.skyxplore.cacheRepository.enabled", havingValue = "true")
public class CachingProductionOrderRepository extends CacheRepository<String, ProductionOrderEntity, String, ProductionOrderRepository> implements ProductionOrderRepository {
    private final UuidConverter uuidConverter;
    private final RequestContextHolder requestContextHolder;

    protected CachingProductionOrderRepository(ProductionOrderRepository repository, CacheContext cacheContext, UuidConverter uuidConverter, RequestContextHolder requestContextHolder) {
        super(repository, ProductionOrderEntity::getGameId, cacheContext);
        this.uuidConverter = uuidConverter;
        this.requestContextHolder = requestContextHolder;
    }

    @Override
    protected List<ProductionOrderEntity> getByKey(String gameId) {
        List<ProductionOrderEntity> entities = repository.getByGameId(gameId);
        log.info("ProductionOrderEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<String> ids) {
        repository.deleteByProductionOrderIdIn(ids);
    }

    @Override
    public void deleteByGameId(String gameId) {
        deleteByKey(gameId);
    }

    @Override
    public void deleteByProductionOrderIdIn(List<String> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public List<ProductionOrderEntity> getByGameId(String gameId) {
        return new ArrayList<>(getMapByKey(gameId).values());
    }

    @Override
    public List<ProductionOrderEntity> getByOrderIdAndPlayerId(String orderId, String playerId) {
        return getMapByKey(getGameId())
            .values()
            .stream()
            .filter(entity -> entity.getOrderId().equals(orderId))
            .filter(entity -> entity.getPlayerId().equals(playerId))
            .collect(Collectors.toList());
    }

    private String getGameId() {
        return uuidConverter.convertDomain(requestContextHolder.get().getGameId());
    }
}
