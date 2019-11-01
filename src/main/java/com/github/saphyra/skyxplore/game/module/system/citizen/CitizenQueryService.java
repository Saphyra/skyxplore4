package com.github.saphyra.skyxplore.game.module.system.citizen;

import com.github.saphyra.skyxplore.game.module.system.citizen.domain.Citizen;
import com.github.saphyra.skyxplore.game.module.system.citizen.domain.CitizenDao;
import com.github.saphyra.skyxplore.game.module.system.citizen.domain.LocationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CitizenQueryService {
    private final CitizenDao citizenDao;

    public List<Citizen> getByLocation(UUID locationId, LocationType locationType) {
        return citizenDao.getByLocation(locationId, locationType);
    }

    public Integer countByLocation(LocationType locationType, UUID locationId) {
        return citizenDao.countByLocation(locationType, locationId);
    }
}
