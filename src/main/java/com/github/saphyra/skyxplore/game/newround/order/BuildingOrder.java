package com.github.saphyra.skyxplore.game.newround.order;

import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.newround.order.processor.construction.BuildingOrderProcessor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class BuildingOrder implements Order{
    @NonNull
    private final Construction construction;

    private final int priority;

    @NonNull
    private final BuildingOrderProcessor buildingOrderProcessor;

    @Override
    public Integer getPriority() {
        return priority * construction.getPriority();
    }

    @Override
    public void process() {
        buildingOrderProcessor.process(this);
    }
}
