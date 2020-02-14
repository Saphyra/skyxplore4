package com.github.saphyra.skyxplore.game.dao.system.citizen;

import com.github.saphyra.skyxplore.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SkillCommandService implements CommandService<Skill> {
    private final SkillDao skillDao;

    @Override
    public void delete(Skill domain) {
        skillDao.delete(domain);
    }

    @Override
    public void deleteAll(List<Skill> domains) {
        skillDao.deleteAll(domains);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        skillDao.deleteByGameId(gameId);
    }

    @Override
    public void save(Skill domain) {
        skillDao.save(domain);
    }

    @Override
    public void saveAll(List<Skill> domains) {
        skillDao.saveAll(domains);
    }

    @Override
    public Class<Skill> getType() {
        return Skill.class;
    }
}
