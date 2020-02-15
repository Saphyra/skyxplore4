package com.github.saphyra.skyxplore_deprecated.game.dao.system.citizen;

import com.github.saphyra.skyxplore_deprecated.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CitizenCommandService implements CommandService<Citizen> {
    private final CitizenDao citizenDao;

    @Override
    public void delete(Citizen domain) {
        citizenDao.delete(domain);
    }

    @Override
    public void deleteAll(List<Citizen> domains) {
        citizenDao.deleteAll(domains);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        citizenDao.deleteByGameId(gameId);
    }

    @Override
    public void save(Citizen domain) {
        citizenDao.save(domain);
    }

    @Override
    public void saveAll(List<Citizen> domains) {
        citizenDao.saveAll(domains);
    }

    @Override
    public Class<Citizen> getType() {
        return Citizen.class;
    }
}
