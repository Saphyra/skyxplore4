package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceCommandService {
    private final ResourceDao resourceDao;
}
