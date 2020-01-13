package com.github.saphyra.skyxplore.game.dao.system.storage.setting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageSettingQueryService {
    private final StorageSettingDao storageSettingDao;
}
