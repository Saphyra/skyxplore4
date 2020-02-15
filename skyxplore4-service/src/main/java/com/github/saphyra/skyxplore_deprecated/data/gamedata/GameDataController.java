package com.github.saphyra.skyxplore_deprecated.data.gamedata;

import com.github.saphyra.skyxplore_deprecated.common.ExceptionFactory;
import com.github.saphyra.skyxplore_deprecated.data.base.AbstractDataService;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.GameDataItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.API_PREFIX;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GameDataController {
    private static final String GET_DATA_MAPPING = API_PREFIX + "/data/{dataId}";

    private final List<AbstractDataService<?, ? extends GameDataItem>> dataServices;

    @GetMapping(GET_DATA_MAPPING)
    GameDataItem getData(@PathVariable("dataId") String dataId) {
        return dataServices.stream()
            .flatMap(abstractDataService -> abstractDataService.values().stream())
            .filter(gameDataItem -> gameDataItem.getId().equals(dataId))
            .findFirst()
            .orElseThrow(() -> ExceptionFactory.dataNotFound(dataId));
    }
}
