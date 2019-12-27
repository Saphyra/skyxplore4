package com.github.saphyra.skyxplore.game.dao.system.citizen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "citizen")
class CitizenEntity {
    @Id
    private String citizenId;
    private String citizenName;
    private String gameId;
    private String userId;
    private String ownerId;
    @Enumerated(EnumType.STRING)
    private LocationType locationType;
    private String locationId;
    private Integer morale;
    private Integer satiety;
}
