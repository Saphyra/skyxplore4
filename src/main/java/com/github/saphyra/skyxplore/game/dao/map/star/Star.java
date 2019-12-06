package com.github.saphyra.skyxplore.game.dao.map.star;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.github.saphyra.skyxplore.game.dao.common.coordinate.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
@Builder
public class Star  {
    @NonNull
    private final UUID starId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID userId;

    @NonNull
    private final String starName;

    @NonNull
    private Coordinate coordinate;

    @NonNull
    private UUID ownerId;

    @NonNull
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "star_id")
    private List<Research> researches;
}
