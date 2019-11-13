package com.github.saphyra.skyxplore.game.module.system.storage.reservation.domain;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ReservationConverter extends ConverterBase<ReservationEntity, Reservation> {
    private final UuidConverter uuidConverter;

    @Override
    protected Reservation processEntityConversion(ReservationEntity entity) {
        return Reservation.builder()
            .reservationId(uuidConverter.convertEntity(entity.getReservationId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .userId(uuidConverter.convertEntity(entity.getUserId()))
            .starId(uuidConverter.convertEntity(entity.getStarId()))
            .dataId(entity.getDataId())
            .amount(entity.getAmount())
            .reservationType(entity.getReservationType())
            .build();
    }

    @Override
    protected ReservationEntity processDomainConversion(Reservation domain) {
        return ReservationEntity.builder()
            .reservationId(uuidConverter.convertDomain(domain.getReservationId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .userId(uuidConverter.convertDomain(domain.getUserId()))
            .starId(uuidConverter.convertDomain(domain.getStarId()))
            .dataId(domain.getDataId())
            .amount(domain.getAmount())
            .reservationType(domain.getReservationType())
            .build();
    }
}
