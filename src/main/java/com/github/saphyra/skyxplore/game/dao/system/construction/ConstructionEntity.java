package com.github.saphyra.skyxplore.game.dao.system.construction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "construction")
class ConstructionEntity {
    @Id
    private String constructionId;
    private String gameId;
    private String starId;
    private String playerId;

    @ElementCollection
    @MapKeyColumn(name = "resource_id")
    @Column(name = "required_amount")
    @CollectionTable(name = "construction_resource_requirements", joinColumns = @JoinColumn(name = "construction_id"))
    private Map<String, Integer> resourceRequirements;

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
}
