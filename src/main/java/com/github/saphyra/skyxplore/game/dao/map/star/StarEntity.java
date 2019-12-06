package com.github.saphyra.skyxplore.game.dao.map.star;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.github.saphyra.skyxplore.game.dao.common.coordinate.CoordinateEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "star")
@NoArgsConstructor
class StarEntity {
    @Id
    private String starId;
    private String gameId;
    private String userId;
    private String starName;
    @Embedded
    private CoordinateEntity coordinates;
    private String ownerId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "star_id")
    private List<ResearchEntity> researches;

}
