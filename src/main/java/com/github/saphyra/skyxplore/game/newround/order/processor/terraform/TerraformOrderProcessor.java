package com.github.saphyra.skyxplore.game.newround.order.processor.terraform;

import com.github.saphyra.skyxplore.game.newround.order.TerraformOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TerraformOrderProcessor {
    public void process(TerraformOrder terraformOrder) {
        log.info("Processing terraformOrder {}", terraformOrder);
    }
}
