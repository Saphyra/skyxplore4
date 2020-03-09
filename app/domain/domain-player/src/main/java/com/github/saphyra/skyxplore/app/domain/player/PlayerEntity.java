package com.github.saphyra.skyxplore.app.domain.player;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "player")
@NoArgsConstructor
class PlayerEntity implements SettablePersistable<String> {
    @Id
    private String playerId;
    private String gameId;
    private String playerName;
    private boolean ai;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return playerId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
