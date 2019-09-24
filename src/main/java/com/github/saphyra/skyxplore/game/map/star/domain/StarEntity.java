package com.github.saphyra.skyxplore.game.map.star.domain;

import com.github.saphyra.skyxplore.game.common.coordinates.domain.CoordinateEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "star")
@NoArgsConstructor
class StarEntity {
    @Id
    @NonNull
    private String starId;

    @NonNull
    private String gameId;

    @NonNull
    private String userId;

    @NonNull
    private String starName;

    @Embedded
    @NonNull
    private CoordinateEntity coordinates;
}
