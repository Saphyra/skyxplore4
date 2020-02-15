package com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.setting;

import com.github.saphyra.skyxplore_deprecated.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class StorageSettingCommandService implements CommandService<StorageSetting> {
    private final StorageSettingDao storageSettingDao;

    @Override
    public void delete(StorageSetting domain) {
        storageSettingDao.delete(domain);
    }

    @Override
    public void deleteAll(List<StorageSetting> domains) {
        storageSettingDao.deleteAll(domains);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        storageSettingDao.deleteByGameId(gameId);
    }

    @Override
    public void save(StorageSetting domain) {
        storageSettingDao.save(domain);
    }

    @Override
    public void saveAll(List<StorageSetting> domains) {
        storageSettingDao.saveAll(domains);
    }

    @Override
    public Class<StorageSetting> getType() {
        return StorageSetting.class;
    }
}
