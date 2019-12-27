package com.github.saphyra.skyxplore.game.dao.system.citizen;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CitizenConverter extends ConverterBase<CitizenEntity, Citizen> {
    private final UuidConverter uuidConverter;

    @Override
    protected Citizen processEntityConversion(CitizenEntity entity) {
        return Citizen.builder()
            .citizenId(uuidConverter.convertEntity(entity.getCitizenId()))
            .citizenName(entity.getCitizenName())
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .ownerId(uuidConverter.convertEntity(entity.getOwnerId()))
            .locationType(entity.getLocationType())
            .locationId(uuidConverter.convertEntity(entity.getLocationId()))
            .morale(entity.getMorale())
            .satiety(entity.getSatiety())
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected CitizenEntity processDomainConversion(Citizen domain) {
        return CitizenEntity.builder()
            .citizenId(uuidConverter.convertDomain(domain.getCitizenId()))
            .citizenName(domain.getCitizenName())
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .ownerId(uuidConverter.convertDomain(domain.getOwnerId()))
            .locationType(domain.getLocationType())
            .locationId(uuidConverter.convertDomain(domain.getLocationId()))
            .morale(domain.getMorale())
            .satiety(domain.getSatiety())
            .isNew(domain.isNew())
            .build();
    }
}
