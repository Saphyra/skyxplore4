package com.github.saphyra.skyxplore.game.dao.system.storage.allocation;

import com.github.saphyra.skyxplore.game.dao.common.cache.SettablePersistable;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
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
@Table(name = "allocation")
class AllocationEntity implements SettablePersistable<String> {
    @Id
    private String allocationId;
    private String gameId;
    private String starId;
    private String playerId;
    private String externalReference;
    private String dataId;

    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private AllocationType allocationType;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return allocationId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
