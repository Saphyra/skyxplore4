package com.github.saphyra.skyxplore.game.game.domain;

import com.github.saphyra.skyxplore.common.Encryptable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@Table(name = "game")
public class Game implements Encryptable {
    @Id
    private UUID gameId;

    private UUID userId;

    private String gameName;

    @Override
    public String getKey() {
        return userId.toString();
    }
}
