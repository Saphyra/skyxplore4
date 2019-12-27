package com.github.saphyra.skyxplore.game.dao.system.building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "building")
class BuildingEntity implements Persistable<String> {
    @Id
    private String buildingId;
    private String buildingDataId;
    private String gameId;
    private String userId;
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
