package com.github.saphyra.skyxplore.game.dao.system.storage.reservation;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReservationConverter extends ConverterBase<ReservationEntity, Reservation> {
    private final UuidConverter uuidConverter;

    @Override
    protected Reservation processEntityConversion(ReservationEntity entity) {
        return Reservation.builder()
            .reservationId(uuidConverter.convertEntity(entity.getReservationId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .starId(uuidConverter.convertEntity(entity.getStarId()))
            .playerId(uuidConverter.convertEntity(entity.getPlayerId()))
            .externalReference(uuidConverter.convertEntity(entity.getExternalReference()))
            .dataId(entity.getDataId())
            .amount(entity.getAmount())
            .storageType(entity.getStorageType())
            .reservationType(entity.getReservationType())
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected ReservationEntity processDomainConversion(Reservation domain) {
        return ReservationEntity.builder()
            .reservationId(uuidConverter.convertDomain(domain.getReservationId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .starId(uuidConverter.convertDomain(domain.getStarId()))
            .playerId(uuidConverter.convertDomain(domain.getPlayerId()))
            .externalReference(uuidConverter.convertDomain(domain.getExternalReference()))
            .dataId(domain.getDataId())
            .amount(domain.getAmount())
            .storageType(domain.getStorageType())
            .reservationType(domain.getReservationType())
            .isNew(domain.isNew())
            .build();
    }
}
