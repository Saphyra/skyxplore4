package com.github.saphyra.skyxplore.app.game_data.domain.resource;

import com.github.saphyra.skyxplore.app.domain.common.StorageType;
import com.github.saphyra.skyxplore.app.game_data.domain.GameDataItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResourceData extends GameDataItem {
    private StorageType storageType;
}
