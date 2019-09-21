package com.github.saphyra.skyxplore.game.common.coordinates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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
