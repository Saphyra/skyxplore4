package com.github.saphyra.skyxplore.game.dao.system.citizen;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CitizenConverter extends ConverterBase<CitizenEntity, Citizen> {
    private final RequestContextHolder requestContextHolder;
    private final StringEncryptor stringEncryptor;
    private final UuidConverter uuidConverter;

    @Override
    protected Citizen processEntityConversion(CitizenEntity entity) {
        return Citizen.builder()
            .citizenId(uuidConverter.convertEntity(entity.getCitizenId()))
            .citizenName(stringEncryptor.decryptEntity(entity.getCitizenName(), uuidConverter.convertDomain(requestContextHolder.get().getUserId())))
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
            .citizenName(stringEncryptor.encryptEntity(domain.getCitizenName(), uuidConverter.convertDomain(requestContextHolder.get().getUserId())))
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
