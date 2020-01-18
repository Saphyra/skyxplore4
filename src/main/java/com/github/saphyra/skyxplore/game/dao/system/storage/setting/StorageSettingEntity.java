package com.github.saphyra.skyxplore.game.dao.system.storage.setting;

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
@Table(name = "storage_setting")
public class StorageSettingEntity implements SettablePersistable<String> {
    @Id
    private String storageSettingId;
    private String gameId;
    private String starId;
    private String playerId;
    private String dataId;
    private Integer targetAmount;
    private Integer priority;
    private Boolean buildable;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return storageSettingId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
