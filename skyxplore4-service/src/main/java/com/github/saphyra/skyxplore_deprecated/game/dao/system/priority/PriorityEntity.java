package com.github.saphyra.skyxplore_deprecated.game.dao.system.priority;

import com.github.saphyra.skyxplore_deprecated.game.dao.common.cache.SettablePersistable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "priority")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
class PriorityEntity implements SettablePersistable<PriorityEntityId> {
    @EmbeddedId
    private PriorityEntityId id;

    private String gameId;

    private String playerId;

    private Integer value;
    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
