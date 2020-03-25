package com.github.saphyra.skyxplore.app.domain.building;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "building")
@Inheritance(strategy = InheritanceType.JOINED)
class BuildingEntity implements SettablePersistable<String> {
    @Id
    private String buildingId;
    private String buildingDataId;
    private String gameId;
    private String starId;
    private String playerId;
    private String surfaceId;
    private Integer level;
    private String constructionId;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return buildingId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
