package com.github.saphyra.skyxplore.game.dao.system.citizen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "skill")
class SkillEntity {
    @Id
    private String skillId;
    @Column(name = "citizen_id")
    private String  citizenId;
    @Enumerated(EnumType.STRING)
    private SkillType skillType;
    private Integer level;
    private Integer experience;
    private Integer nextLevel;
}
