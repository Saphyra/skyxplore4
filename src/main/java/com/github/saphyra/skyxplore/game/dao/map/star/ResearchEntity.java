package com.github.saphyra.skyxplore.game.dao.map.star;

import javax.persistence.Column;
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
@Table(name = "research")
class ResearchEntity {
    @Id
    private String researchId;

    @Column(name = "star_id")
    private String starId;

    private String dataId;
}
