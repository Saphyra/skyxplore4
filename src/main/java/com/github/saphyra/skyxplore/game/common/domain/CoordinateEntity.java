package com.github.saphyra.skyxplore.game.common.domain;

import lombok.*;

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
