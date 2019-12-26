package com.github.saphyra.skyxplore.game.dao.system.citizen;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class CitizenCommandService {
    private final CitizenDao citizenDao;

    public void saveAll(List<Citizen> citizens) {
        citizenDao.saveAll(citizens);
    }
}
