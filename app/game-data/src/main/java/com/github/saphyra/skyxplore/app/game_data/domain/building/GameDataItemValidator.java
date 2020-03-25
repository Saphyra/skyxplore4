package com.github.saphyra.skyxplore.app.game_data.domain.building;

import static java.util.Objects.requireNonNull;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.data.DataValidator;
import com.github.saphyra.skyxplore.app.game_data.domain.GameDataItem;

@Component
//TODO unit test
public class GameDataItemValidator implements DataValidator<GameDataItem> {
    @Override
    public void validate(GameDataItem item) {
        requireNonNull(item.getId(), "Id must not be null.");
    }
}
