package com.github.saphyra.skyxplore.game.map.surface.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.github.saphyra.skyxplore.game.common.coordinates.domain.CoordinateEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "surface")
class SurfaceEntity {
    @Id
    private String surfaceId;

    private String starId;

    private String userId;

    private String gameId;

    @Embedded
    private CoordinateEntity coordinate;

    private String surfaceType;

    private String buildingId;
}
