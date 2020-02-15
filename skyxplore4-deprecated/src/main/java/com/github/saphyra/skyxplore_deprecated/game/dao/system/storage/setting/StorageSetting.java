package com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.setting;

import com.github.saphyra.skyxplore_deprecated.game.common.interfaces.QueueType;
import com.github.saphyra.skyxplore_deprecated.game.common.interfaces.Queueable;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.priority.PriorityType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class StorageSetting implements Queueable {
    @NonNull
    private final UUID storageSettingId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID starId;

    @NonNull
    private final UUID playerId;

    @NonNull
    private final String dataId;

    @NonNull
    private Integer targetAmount;

    @NonNull
    private Integer priority;

    @NonNull
    private Integer batchSize;

    @NonNull
    private final Boolean buildable;

    private final boolean isNew;

    @Override
    public QueueType getQueueType() {
        return QueueType.PRODUCTION;
    }

    @Override
    public UUID getId() {
        return storageSettingId;
    }

    @Override
    public OffsetDateTime addedAt() {
        return OffsetDateTime.now();
    }

    @Override
    public PriorityType getPriorityType() {
        return buildable ? PriorityType.PRODUCTION : PriorityType.MINING;
    }
}
