package com.github.saphyra.skyxplore_deprecated.game.dao.system.citizen;

import com.github.saphyra.skyxplore_deprecated.game.dao.common.cache.SettablePersistable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
@Table(name = "skill")
class SkillEntity implements SettablePersistable<String> {
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
