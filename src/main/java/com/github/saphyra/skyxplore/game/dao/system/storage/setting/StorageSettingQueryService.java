package com.github.saphyra.skyxplore.game.dao.system.storage.setting;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageSettingQueryService {
    private final RequestContextHolder requestContextHolder;
    private final StorageSettingDao storageSettingDao;

    public List<StorageSetting> getByStarIdAndPlayerId(UUID starId) {
        RequestContext requestContext = requestContextHolder.get();
        UUID playerId = requestContext.getPlayerId();
        return storageSettingDao.getByStarIdAndPlayerId(starId, playerId);
    }
}
