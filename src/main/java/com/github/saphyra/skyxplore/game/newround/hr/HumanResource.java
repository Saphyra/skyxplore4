package com.github.saphyra.skyxplore.game.newround.hr;

import com.github.saphyra.skyxplore.game.dao.system.citizen.Citizen;
import com.github.saphyra.skyxplore.game.dao.system.citizen.Skill;
import com.github.saphyra.skyxplore.game.dao.system.citizen.SkillType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class HumanResource {
    @NonNull
    private final Citizen citizen;

    private UUID allocation;

    private volatile int workPointsLeft;

    @NonNull
    private Map<SkillType, Skill> skills;

    public boolean isDepleted() {
        return workPointsLeft == 0;
    }

    public Integer getProductivity(SkillType requiredSkill) {
        return skills.get(requiredSkill).getLevel() * citizen.getMorale();
    }

    public int produce(SkillType requiredSkill, int targetAmount, int workPointsPerItem) {
        //TODO implement
        //TODO handle items with higher workPoints
        return 0;
    }
}
