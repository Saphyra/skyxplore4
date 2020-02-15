package com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.research;


import com.github.saphyra.skyxplore_deprecated.data.base.DataValidator;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Component
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
