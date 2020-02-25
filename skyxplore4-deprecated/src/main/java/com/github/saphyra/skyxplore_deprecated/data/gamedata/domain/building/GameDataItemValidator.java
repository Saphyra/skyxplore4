package com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building;

import com.github.saphyra.skyxplore.app.common.data.DataValidator;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.GameDataItem;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class GameDataItemValidator implements DataValidator<GameDataItem> {
    @Override
    public void validate(GameDataItem item) {
        requireNonNull(item.getId(), "Id must not be null.");
    }
}
