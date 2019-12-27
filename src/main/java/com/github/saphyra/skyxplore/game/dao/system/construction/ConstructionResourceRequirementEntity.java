package com.github.saphyra.skyxplore.game.dao.system.construction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "construction_resource_requirement")
class ConstructionResourceRequirementEntity {
    @Id
    private String constructionResourceRequirementId;
    private String gameId;
    private String constructionId;
    private String resourceId;
    private Integer requiredAmount;
}
