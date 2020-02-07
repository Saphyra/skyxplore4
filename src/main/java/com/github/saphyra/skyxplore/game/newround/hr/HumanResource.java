package com.github.saphyra.skyxplore.game.newround.hr;

import com.github.saphyra.skyxplore.game.dao.system.citizen.Citizen;
import com.github.saphyra.skyxplore.game.dao.system.citizen.Skill;
import com.github.saphyra.skyxplore.game.dao.system.citizen.SkillType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@Slf4j
public class HumanResource {
    @NonNull
    private final HumanResourceContext context;

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

    public ProductionProcess produce(SkillType requiredSkill, int targetAmount, int workPointsPerItem, Integer currentProgress) {
        log.info("Production started. requiredSkill: {}, targetAmount: {}, workPointsPerItem: {}, currentProgress: {}", requiredSkill, targetAmount, workPointsPerItem, currentProgress);
        //TODO moral multiplier
        int skillMultiplier = 1 + (skills.get(requiredSkill).getLevel() - 1) / 10;
        int availableWorkPoints = workPointsLeft * skillMultiplier;
        log.info("availableWorkPoints for HumanResource {}: {}", citizen.getCitizenId(), availableWorkPoints);

        int producedAmount = 0;
        if (currentProgress > 0) {
            int missingWorkPoints = workPointsPerItem - currentProgress;
            log.info("Missing workPoints to complete the inProgress production: {}", missingWorkPoints);
            if (missingWorkPoints > availableWorkPoints) {
                log.info("Citizen has not enough availableWorkPoints to complete the inProgress production.");
                currentProgress += availableWorkPoints;
                availableWorkPoints = 0;
            } else {
                log.info("Citizen has availableWorkPoints to complete the inProgress production.");
                availableWorkPoints -= missingWorkPoints;
                producedAmount++;
                currentProgress = 0;
            }

            log.info("Status after finishing the inProgress production: producedAmount: {}, availableWorkPoints: {}, currentProcess: {}", producedAmount, availableWorkPoints, currentProgress);
        }

        int producableAmount = Math.floorDiv(availableWorkPoints, workPointsPerItem);
        int missingAmount = targetAmount - producedAmount;
        log.info("producableAmount: {}, missingAmount: {}", producableAmount, missingAmount);
        if (producableAmount < missingAmount) {
            log.info("Citizen has not enough availableWorkPoints to complete all of the productions.");
            producedAmount += producableAmount;
            availableWorkPoints -= producableAmount * workPointsPerItem;
            currentProgress = availableWorkPoints;
            workPointsLeft = 0;
        } else {
            log.info("Citizen has enough availableWorkPoints to complete all of the productions.");
            producedAmount += missingAmount;
            availableWorkPoints -= missingAmount * workPointsPerItem;
            currentProgress = 0;
            workPointsLeft = availableWorkPoints / skillMultiplier;
        }
        log.info("producedAmount: {}, currentProgress: {}, workPointsLeft: {}", producedAmount, currentProgress, workPointsLeft);
        //TODO earn skill points for each production
        //TODO reduce moral wen working
        ProductionProcess result = ProductionProcess.builder()
            .finishedProducts(producedAmount)
            .currentProgress(currentProgress)
            .build();
        log.info("ProductionProcess: {}", result);
        return result;
    }
}
