package com.github.saphyra.skyxplore.game.dao.system.storage.reservation;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
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
    private String externalReference;
    private String dataId;
    private Integer amount;

    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    @Enumerated(EnumType.STRING)
    private ReservationType reservationType;
}
