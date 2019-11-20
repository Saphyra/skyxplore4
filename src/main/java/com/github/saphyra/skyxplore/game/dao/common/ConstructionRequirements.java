package com.github.saphyra.skyxplore.game.dao.common;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConstructionRequirements {
    private Integer workPoints;
    private List<String> research;
    private Map<String, Integer> resources;
}
