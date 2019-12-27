package com.github.saphyra.skyxplore.game.dao.map.star;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "research")
class ResearchEntity {
    @Id
    private String researchId;
    private String gameId;
    private String playerId;
    private String starId;
    private String dataId;
}
