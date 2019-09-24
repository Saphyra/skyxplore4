package com.github.saphyra.skyxplore.game.common.coordinates.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CoordinateEntity {
    @NonNull
    @Column(name = "x_coord")
    private String x;

    @NonNull
    @Column(name = "y_coord")
    private String y;
}
