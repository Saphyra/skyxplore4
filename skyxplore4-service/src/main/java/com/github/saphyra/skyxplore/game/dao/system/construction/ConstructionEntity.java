package com.github.saphyra.skyxplore.game.dao.system.construction;

import com.github.saphyra.skyxplore.game.dao.common.cache.SettablePersistable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "construction")
class ConstructionEntity implements SettablePersistable<String> {
    @Id
    private String constructionId;
    private String gameId;
    private String starId;
    private String playerId;
    private Integer requiredWorkPoints;
    private Integer currentWorkPoints;

    @Enumerated(EnumType.STRING)
    private ConstructionType constructionType;

    @Enumerated(EnumType.STRING)
    private ConstructionStatus constructionStatus;
    private Integer priority;
    private String externalId;
    private String surfaceId;
    private String additionalData;
    private Long addedAt;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return constructionId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
