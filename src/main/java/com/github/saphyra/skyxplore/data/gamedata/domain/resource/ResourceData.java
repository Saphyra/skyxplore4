package com.github.saphyra.skyxplore.data.gamedata.domain.resource;

import com.github.saphyra.skyxplore.data.gamedata.domain.GameDataItem;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResourceData extends GameDataItem {
    private StorageType storageType;
}
