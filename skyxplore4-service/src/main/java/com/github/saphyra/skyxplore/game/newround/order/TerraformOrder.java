package com.github.saphyra.skyxplore.game.newround.order;

import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.newround.order.processor.construction.TerraformOrderProcessor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class TerraformOrder implements Order {
    @NonNull
    private final Construction construction;

    private final int priority;

    @NonNull
    private final TerraformOrderProcessor terraformOrderProcessor;

    @Override
    public Integer getPriority() {
        return priority * construction.getPriority();
    }

    @Override
    public void process() {
        terraformOrderProcessor.process(this);
    }
}
