package com.github.saphyra.skyxplore.game.dao.system.citizen;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class SkillDao extends AbstractDao<SkillEntity, Skill, String, SkillRepository> {
    private final UuidConverter uuidConverter;

    public SkillDao(SkillConverter converter, SkillRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameId(UUID gameId) {
        log.info("Deleting skill for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    @Override
    public void saveAll(List<Skill> skills) {
        repository.saveAll(converter.convertDomain(skills));
    }

    List<Skill> getByCitizenId(UUID citizenId) {
        return converter.convertEntity(repository.getByCitizenId(
            uuidConverter.convertDomain(citizenId)
        ));
    }
}
