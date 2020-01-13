package com.github.saphyra.skyxplore.game.dao.system.storage.setting;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StorageSettingDao extends AbstractDao<StorageSettingEntity, StorageSetting, String, StorageSettingRepository> {
    private final UuidConverter uuidConverter;

    public StorageSettingDao(StorageSettingConverter converter, StorageSettingRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameId(UUID gameId) {
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }
}
