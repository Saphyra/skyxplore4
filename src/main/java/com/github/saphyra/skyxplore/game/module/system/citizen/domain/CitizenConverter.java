package com.github.saphyra.skyxplore.game.module.system.citizen.domain;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class CitizenConverter extends ConverterBase<CitizenEntity, Citizen> {
    private final SkillConverter skillConverter;
    private final UuidConverter uuidConverter;

    @Override
    protected Citizen processEntityConversion(CitizenEntity entity) {
        return Citizen.builder()
                .citizenId(uuidConverter.convertEntity(entity.getCitizenId()))
                .citizenName(entity.getCitizenName())
                .gameId(uuidConverter.convertEntity(entity.getGameId()))
                .userId(uuidConverter.convertEntity(entity.getUserId()))
                .locationType(entity.getLocationType())
                .locationId(uuidConverter.convertEntity(entity.getLocationId()))
                .morale(entity.getMorale())
                .satiety(entity.getSatiety())
                .skills(mapSkills(entity.getSkills()))
                .build();
    }

    private Map<SkillType, Skill> mapSkills(List<SkillEntity> skills) {
        return skillConverter.convertEntity(skills).stream()
                .collect(Collectors.toMap(Skill::getSkillType, skill -> skill));
    }

    @Override
    protected CitizenEntity processDomainConversion(Citizen domain) {
        return CitizenEntity.builder()
                .citizenId(uuidConverter.convertDomain(domain.getCitizenId()))
                .citizenName(domain.getCitizenName())
                .gameId(uuidConverter.convertDomain(domain.getGameId()))
                .userId(uuidConverter.convertDomain(domain.getUserId()))
                .locationType(domain.getLocationType())
                .locationId(uuidConverter.convertDomain(domain.getLocationId()))
                .morale(domain.getMorale())
                .satiety(domain.getSatiety())
                .skills(mapSkills(domain.getSkills()))
                .build();
    }

    private List<SkillEntity> mapSkills(Map<SkillType, Skill> skills) {
        return skillConverter.convertDomain(new ArrayList<>(skills.values()));
    }
}
