package com.github.saphyra.skyxplore.game.dao.map.connection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "star_connection")
class StarConnectionEntity {
    @Id
    private String connectionId;
    private String gameId;
    private String userId;
    private String star1;
    private String star2;
}
