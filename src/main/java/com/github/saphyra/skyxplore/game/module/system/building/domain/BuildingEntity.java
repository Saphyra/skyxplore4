package com.github.saphyra.skyxplore.game.module.system.building.domain;

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
@Table(name = "building")
class BuildingEntity {
    @Id
    private String buildingId;
    private String buildingDataId;
    private String gameId;
    private String userId;
    private String starId;
    private Integer level;
    private String constructionId;
}
