package com.github.saphyra.skyxplore.game.dao.system.storage.buildingcache;

import com.github.saphyra.skyxplore.game.dao.common.cache.SettablePersistable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "building_cache")
public class BuildingCacheEntity implements SettablePersistable<String> {
    @Id
    private String buildingCacheId;
    private String gameId;
    private String playerId;
    private String buildingId;
    private String dataId;
    private Integer amount;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return buildingCacheId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
