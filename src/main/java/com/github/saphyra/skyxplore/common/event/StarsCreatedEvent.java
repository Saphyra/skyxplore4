package com.github.saphyra.skyxplore.common.event;

import java.util.List;

import com.github.saphyra.skyxplore.game.map.star.domain.Star;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class StarsCreatedEvent {
    @NonNull
    private final List<Star> stars;
}
