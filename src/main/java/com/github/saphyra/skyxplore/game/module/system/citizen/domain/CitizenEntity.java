package com.github.saphyra.skyxplore.game.module.system.citizen.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private LocationType locationType;
    private String locationId;
    private Integer morale;
    private Integer satiety;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "citizen_id")
    private List<SkillEntity> skills;
}
