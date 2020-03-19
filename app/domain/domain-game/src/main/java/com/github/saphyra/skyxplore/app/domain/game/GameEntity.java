package com.github.saphyra.skyxplore.app.domain.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "game")
class GameEntity {
    @Id
    @NonNull
    private String gameId;

    @NonNull
    private String userId;

    @NonNull
    private String gameName;

    @NonNull
    private Integer round;
}