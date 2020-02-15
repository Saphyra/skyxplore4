package com.github.saphyra.skyxplore_deprecated.game.newround.order.processor.construction;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.reservation.Reservation;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.reservation.ReservationCommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.reservation.ReservationQueryService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.skyxplore_deprecated.game.service.system.storage.reservation.ReservationFactory;
import com.github.saphyra.util.OptionalMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class ReservationUpdateService {
    private final ReservationCommandService reservationCommandService;
    private final ReservationFactory reservationFactory;
    private final ReservationQueryService reservationQueryService;

    boolean updateReservations(Construction construction, Map<String, Integer> requiredResources, OptionalMap<String, Allocation> allocations) {
        boolean allResourcesPresent = true;
        for (String dataId : requiredResources.keySet()) {
            int requiredAmount = requiredResources.get(dataId);
            int amountToReserve = requiredAmount - allocations.get(dataId).getAmount();

            Optional<Reservation> optionalReservation = reservationQueryService.findByExternalReferenceAndDataIdAndPlayerId(construction.getConstructionId(), dataId);
            if (amountToReserve > 0) {
                allResourcesPresent = false;
                Reservation reservation = optionalReservation.orElseGet(() -> createReservation(construction, dataId));

                reservation.setAmount(amountToReserve);
                reservationCommandService.save(reservation);
            } else {
                optionalReservation.ifPresent(reservationCommandService::delete);
            }
        }
        return allResourcesPresent;
    }

    private Reservation createReservation(Construction construction, String dataId) {
        return reservationFactory.create(
            construction.getGameId(),
            construction.getStarId(),
            dataId,
            0,
            ReservationType.TERRAFORMING,
            construction.getConstructionId(),
            construction.getPlayerId()
        );
    }
}
