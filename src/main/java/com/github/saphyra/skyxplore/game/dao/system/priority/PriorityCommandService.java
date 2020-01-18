package com.github.saphyra.skyxplore.game.dao.system.priority;

import com.github.saphyra.skyxplore.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriorityCommandService implements CommandService<Priority> {
    private final PriorityDao priorityDao;

    @Override
    public void delete(Priority domain) {
        priorityDao.delete(domain);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        priorityDao.deleteByGameId(gameId);
    }

    @Override
    public void save(Priority domain) {
        priorityDao.save(domain);
    }

    @Override
    public void saveAll(List<Priority> domains) {
        priorityDao.saveAll(domains);
    }

    @Override
    public Class<Priority> getType() {
        return Priority.class;
    }
}
