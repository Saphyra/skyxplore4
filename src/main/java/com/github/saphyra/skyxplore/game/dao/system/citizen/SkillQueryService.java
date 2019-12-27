package com.github.saphyra.skyxplore.game.dao.system.citizen;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SkillQueryService {
    private final SkillDao skillDao;

    public List<Skill> getByCitizenId(UUID citizenId) {
        return skillDao.getByCitizenId(citizenId);
    }
}
