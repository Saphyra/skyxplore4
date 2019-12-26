package com.github.saphyra.skyxplore.game.service.system.citizen.service.creation;

import com.github.saphyra.skyxplore.game.dao.map.star.Star;
import com.github.saphyra.skyxplore.game.dao.system.citizen.Citizen;
import com.github.saphyra.skyxplore.game.dao.system.citizen.CitizenCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class CitizenCreationService {
    private final CitizenCreationProperties properties;
    private final CitizenCommandService citizenCommandService;
    private final CitizenFactory citizenFactory;

    public void createCitizens(List<Star> stars) {
        log.info("Creating citizens...");
        List<Citizen> citizens = stars
            .parallelStream()
            .flatMap(this::createCitizens)
            .collect(Collectors.toList());
        log.info("Number of citizens: {}", citizens.size());
        citizenCommandService.saveAll(citizens);
    }

    private Stream<Citizen> createCitizens(Star star) {
        return Stream.generate(Object::new)
            .limit(properties.getInitialAmount())
            .map(o -> citizenFactory.create(star));
    }
}
