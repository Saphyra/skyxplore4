package com.github.saphyra.skyxplore.app.domain.coordinate;

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
    private Integer x;

    @NonNull
    @Column(name = "y_coord")
    private Integer y;
}
