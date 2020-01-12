package com.github.saphyra.skyxplore.game.dao.system.construction;

import com.github.saphyra.skyxplore.game.dao.common.cache.SettablePersistable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "construction_resource_requirement")
class ConstructionResourceRequirementEntity implements SettablePersistable<String> {
    @Id
    private String constructionResourceRequirementId;
    private String gameId;
    private String constructionId;
    private String resourceId;
    private Integer requiredAmount;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return constructionResourceRequirementId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
