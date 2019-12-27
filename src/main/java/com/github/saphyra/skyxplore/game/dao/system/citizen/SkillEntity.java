package com.github.saphyra.skyxplore.game.dao.system.citizen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "skill")
class SkillEntity implements Persistable<String> {
    @Id
    private String skillId;
    @Column(name = "citizen_id")
    private String citizenId;
    private String gameId;
    @Enumerated(EnumType.STRING)
    private SkillType skillType;
    private Integer level;
    private Integer experience;
    private Integer nextLevel;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return skillId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
