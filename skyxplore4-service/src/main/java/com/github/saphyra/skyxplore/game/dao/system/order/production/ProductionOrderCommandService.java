package com.github.saphyra.skyxplore.game.dao.system.order.production;

import com.github.saphyra.skyxplore.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductionOrderCommandService implements CommandService<ProductionOrder> {
    private final ProductionOrderDao productionOrderDao;

    @Override
    public void delete(ProductionOrder domain) {
        productionOrderDao.delete(domain);
    }

    @Override
    public void deleteAll(List<ProductionOrder> domains) {
        productionOrderDao.deleteAll(domains);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        productionOrderDao.deleteByGameId(gameId);
    }

    @Override
    public void save(ProductionOrder domain) {
        productionOrderDao.save(domain);
    }

    @Override
    public void saveAll(List<ProductionOrder> domains) {
        productionOrderDao.saveAll(domains);
    }

    @Override
    public Class<ProductionOrder> getType() {
        return ProductionOrder.class;
    }
}
