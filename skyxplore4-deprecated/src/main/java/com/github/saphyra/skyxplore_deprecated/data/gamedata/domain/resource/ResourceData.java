package com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.resource;

import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.GameDataItem;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.StorageType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResourceData extends GameDataItem {
    private StorageType storageType;
}
