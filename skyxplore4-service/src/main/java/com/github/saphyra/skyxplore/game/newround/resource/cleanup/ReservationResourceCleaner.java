package com.github.saphyra.skyxplore.game.newround.resource.cleanup;

import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.Reservation;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationResourceCleaner implements ResourceCleaner {
    private final ReservationCommandService reservationCommandService;
    private final ReservationQueryService reservationQueryService;

    @Override
    public void clean() {
        List<Reservation> reservations = reservationQueryService.getByGameId()
            .stream()
            .filter(reservation -> reservation.getAmount() == 0)
            .collect(Collectors.toList());

        reservationCommandService.deleteAll(reservations);
    }
}
