package com.github.saphyra.skyxplore_deprecated.game.dao.system.citizen;

import com.github.saphyra.skyxplore_deprecated.game.dao.common.cache.SettablePersistable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "citizen")
class CitizenEntity implements SettablePersistable<String> {
    @Id
    private String citizenId;
    private String citizenName;
    private String gameId;
    private String ownerId;
    @Enumerated(EnumType.STRING)
    private LocationType locationType;
    private String locationId;
    private Integer morale;
    private Integer satiety;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return citizenId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
