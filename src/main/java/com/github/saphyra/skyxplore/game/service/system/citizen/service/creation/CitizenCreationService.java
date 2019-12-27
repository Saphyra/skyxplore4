package com.github.saphyra.skyxplore.game.service.system.citizen.service.creation;

import com.github.saphyra.skyxplore.game.common.DomainSaverService;
import com.github.saphyra.skyxplore.game.dao.map.star.Star;
import com.github.saphyra.skyxplore.game.dao.system.citizen.Citizen;
import com.github.saphyra.skyxplore.game.dao.system.citizen.Skill;
import com.github.saphyra.skyxplore.game.dao.system.citizen.SkillType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class CitizenCreationService {
    private final CitizenCreationProperties properties;
    private final CitizenFactory citizenFactory;
    private final DomainSaverService domainSaverService;
    private final SkillFactory skillFactory;

    public void createCitizens(List<Star> stars) {
        log.info("Creating citizens...");
        List<Citizen> citizens = stars.stream()
            .parallel()
            .flatMap(this::createCitizens)
            .collect(Collectors.toList());
        log.info("Number of citizens: {}", citizens.size());
        log.info("Creating skills for citizens...");
        List<Skill> skills = citizens.stream()
            .parallel()
            .flatMap(this::generateSkills)
            .collect(Collectors.toList());
        log.info("Number of skills: {}", skills.size());
        domainSaverService.addAll(citizens);
        domainSaverService.addAll(skills);
    }

    private Stream<Citizen> createCitizens(Star star) {
        return Stream.generate(Object::new)
            .limit(properties.getInitialAmount())
            .map(o -> citizenFactory.create(star));
    }

    private Stream<Skill> generateSkills(Citizen citizen) {
        return Arrays.stream(SkillType.values())
            .map(skillType -> skillFactory.create(skillType, citizen.getCitizenId(), citizen.getGameId()));
    }
}
