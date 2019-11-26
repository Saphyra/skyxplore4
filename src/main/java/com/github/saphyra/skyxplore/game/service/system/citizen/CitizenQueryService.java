package com.github.saphyra.skyxplore.game.service.system.citizen;

import com.github.saphyra.skyxplore.game.dao.system.citizen.Citizen;
import com.github.saphyra.skyxplore.game.dao.system.citizen.CitizenDao;
import com.github.saphyra.skyxplore.game.dao.system.citizen.LocationType;
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