package com.github.saphyra.skyxplore.game.dao.map.surface;

import com.github.saphyra.skyxplore.game.dao.common.cache.SettablePersistable;
import com.github.saphyra.skyxplore.game.dao.common.coordinate.CoordinateEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "surface")
class SurfaceEntity implements SettablePersistable<String> {
    @Id
    private String surfaceId;

    private String starId;

    private String gameId;

    private String playerId;

    @Embedded
    private CoordinateEntity coordinate;

    @Enumerated(EnumType.STRING)
    private SurfaceType surfaceType;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return surfaceId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
