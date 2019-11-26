package com.github.saphyra.skyxplore.game.dao.map.star;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.github.saphyra.skyxplore.game.dao.common.coordinate.CoordinateEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "star")
@NoArgsConstructor
class StarEntity {
    @Id
    @NonNull
    private String starId;

    @NonNull
    private String gameId;

    @NonNull
    private String userId;

    @NonNull
    private String starName;

    @Embedded
    @NonNull
    private CoordinateEntity coordinates;

    private String ownerId;
}