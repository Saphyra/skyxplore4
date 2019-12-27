package com.github.saphyra.skyxplore.game.dao.system.citizen;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SkillConverter extends ConverterBase<SkillEntity, Skill> {
    private final UuidConverter uuidConverter;

    @Override
    protected Skill processEntityConversion(SkillEntity entity) {
        return Skill.builder()
            .skillId(uuidConverter.convertEntity(entity.getSkillId()))
            .citizenId(uuidConverter.convertEntity(entity.getCitizenId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .skillType(entity.getSkillType())
            .level(entity.getLevel())
            .experience(entity.getExperience())
            .nextLevel(entity.getNextLevel())
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected SkillEntity processDomainConversion(Skill domain) {
        return SkillEntity.builder()
            .skillId(uuidConverter.convertDomain(domain.getSkillId()))
            .citizenId(uuidConverter.convertDomain(domain.getCitizenId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .skillType(domain.getSkillType())
            .level(domain.getLevel())
            .experience(domain.getExperience())
            .nextLevel(domain.getNextLevel())
            .isNew(domain.isNew())
            .build();
    }
}
