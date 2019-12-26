package com.github.saphyra.skyxplore.game.dao.system.storage.reservation;

import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

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
    private final UUID playerId;

    @NonNull
    private final UUID externalReference;

    @NonNull
    private final String dataId;

    @NonNull
    private Integer amount;

    @NonNull
    private StorageType storageType;

    @NonNull
    private final ReservationType reservationType;
}
