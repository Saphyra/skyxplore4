package com.github.saphyra.skyxplore.game.module.system.storage.reservation.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "reservation")
class ReservationEntity {
    @Id
    private String reservationId;
    private String gameId;
    private String userId;
    private String starId;
    private String dataId;
    private Integer amount;

    @Enumerated(EnumType.STRING)
    private ReservationType reservationType;
}
