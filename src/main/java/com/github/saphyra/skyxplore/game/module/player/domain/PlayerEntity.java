package com.github.saphyra.skyxplore.game.module.player.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
class PlayerEntity {
    @Id
    private String playerId;
    private String gameId;
    private String userId;
    private String playerName;
    private boolean ai;
}
