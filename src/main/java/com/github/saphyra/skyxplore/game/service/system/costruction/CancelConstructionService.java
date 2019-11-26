package com.github.saphyra.skyxplore.game.service.system.costruction;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionDao;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationDao;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
class CancelConstructionService {
    private final AllocationDao allocationDao;
    private final ConstructionDao constructionDao;
    private final ConstructionQueryService constructionQueryService;
    private final ReservationDao reservationDao;

    @SuppressWarnings("WeakerAccess")
    @Transactional
    public void cancelConstruction(UUID constructionId) {
        Construction construction = constructionQueryService.findByConstructionId(constructionId);
        allocationDao.deleteByExternalReference(constructionId);
        reservationDao.deleteByExternalReference(constructionId);

        constructionDao.delete(construction);
    }
}
