package com.github.saphyra.skyxplore.game.dao.map.surface;

import com.github.saphyra.skyxplore.game.dao.common.coordinate.CoordinateEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    private SurfaceType surfaceType;
}
