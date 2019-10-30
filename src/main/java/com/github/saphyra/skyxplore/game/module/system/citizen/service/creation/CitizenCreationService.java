package com.github.saphyra.skyxplore.game.module.system.citizen.service.creation;

import com.github.saphyra.skyxplore.game.module.map.star.domain.Star;
import com.github.saphyra.skyxplore.game.module.system.citizen.domain.Citizen;
import com.github.saphyra.skyxplore.game.module.system.citizen.domain.CitizenDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CitizenCreationService {
    private final CitizenCreationProperties properties;
    private final CitizenDao citizenDao;
    private final CitizenFactory citizenFactory;

    public void createCitizens(List<Star> stars) {
        stars
                //.parallelStream()
                .forEach(this::createCitizens);
    }

    private void createCitizens(Star star) {
        List<Citizen> citizens = Stream.generate(Object::new).limit(properties.getInitialAmount())
                .map(o -> citizenFactory.create(star))
                .collect(Collectors.toList());
        citizenDao.saveAll(citizens);
    }
}
