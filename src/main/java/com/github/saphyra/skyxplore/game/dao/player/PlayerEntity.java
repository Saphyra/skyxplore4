package com.github.saphyra.skyxplore.game.dao.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "player")
@NoArgsConstructor
class PlayerEntity implements Persistable<String> {
    @Id
    private String playerId;
    private String gameId;
    private String userId;
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
