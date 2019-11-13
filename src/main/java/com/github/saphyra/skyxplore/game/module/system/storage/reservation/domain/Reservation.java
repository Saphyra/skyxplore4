package com.github.saphyra.skyxplore.game.module.system.storage.reservation.domain;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
//Used for reserving storage where the non-existing resources will be stored once they are producted
public class Reservation {
    @NonNull
    private final UUID reservationId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID userId;

    @NonNull
    private final UUID starId;

    @NonNull
    private final String dataId;

    @NonNull
    private Integer amount;

    @NonNull
    private final ReservationType reservationType;
}
