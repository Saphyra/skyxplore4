package com.github.saphyra.skyxplore.app.game_data.domain.research;


import static java.util.Objects.requireNonNull;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.data.DataValidator;

@Component
//TODO unit test
public class UsedInValidator implements DataValidator<List<Unlock>> {
    @Override
    public void validate(List<Unlock> item) {
        item.forEach(this::validate);
    }

    private void validate(Unlock unlock) {
        requireNonNull(unlock, "Unlock must not be null.");
        requireNonNull(unlock.getDataId(), "DataId must not be null.");
        requireNonNull(unlock.getType(), "Type must not be null.");
    }
}
