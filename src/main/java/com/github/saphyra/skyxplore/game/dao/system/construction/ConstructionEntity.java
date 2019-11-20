package com.github.saphyra.skyxplore.game.dao.system.construction;

import java.util.Map;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String userId;
    private String starId;

    @ElementCollection
    @MapKeyColumn(name = "resource_id")
    @Column(name = "required_amount")
    @CollectionTable(name = "construction_resource_requirements", joinColumns = @JoinColumn(name = "construction_id"))
    private Map<String, Integer> resourceRequirements;

    private Integer workPoints;

    private Integer currentWorkPoints;

    @Enumerated(EnumType.STRING)
    private ConstructionType constructionType;

    @Enumerated(EnumType.STRING)
    private ConstructionStatus constructionStatus;

    private Integer priority;

    private String externalId;
}
