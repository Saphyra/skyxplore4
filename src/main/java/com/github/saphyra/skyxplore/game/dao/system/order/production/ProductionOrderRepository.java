package com.github.saphyra.skyxplore.game.dao.system.order.production;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
interface ProductionOrderRepository extends CrudRepository<ProductionOrderEntity, String> {
    @Modifying
    @Query("DELETE FROM ProductionOrderEntity e WHERE e.gameId = :gameId")
    @Transactional
    void deleteByGameId(@Param("gameId") String gameId);

    @Transactional
    void deleteByProductionOrderIdIn(List<String> ids);

    List<ProductionOrderEntity> getByGameId(String gameId);

    List<ProductionOrderEntity> getByOrderIdAndPlayerId(String orderId, String playerId);

    List<ProductionOrderEntity> getByProducerBuildingIdAndPlayerId(String producerBuildingId, String playerId);

    List<ProductionOrderEntity> getByCustomerIdAndPlayerId(String customerId, String playerId);
}
