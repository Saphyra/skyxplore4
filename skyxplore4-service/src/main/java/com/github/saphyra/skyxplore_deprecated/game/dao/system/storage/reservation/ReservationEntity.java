package com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.reservation;

import com.github.saphyra.skyxplore_deprecated.game.dao.common.cache.SettablePersistable;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.StorageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservation")
class ReservationEntity implements SettablePersistable<String> {
    @Id
    private String reservationId;
    private String gameId;
    private String starId;
    private String playerId;
    private String externalReference;
    private String dataId;
    private Integer amount;

    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    @Enumerated(EnumType.STRING)
    private ReservationType reservationType;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return reservationId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
