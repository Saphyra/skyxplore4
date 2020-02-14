package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

import com.github.saphyra.skyxplore.game.dao.common.cache.SettablePersistable;
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

@Entity
@Table(name = "resource")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ResourceEntity implements SettablePersistable<String> {
    @Id
    private String resourceId;
    private String gameId;
    private String playerId;
    @Enumerated(EnumType.STRING)
    private StorageType storageType;
    private String dataId;
    private String starId;
    private Integer amount;
    private Integer round;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return resourceId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
