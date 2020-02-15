package com.github.saphyra.skyxplore_deprecated.game.newround.hr;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.citizen.CitizenQueryService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.citizen.SkillType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class HumanResourceService {
    private final Map<UUID, Map<UUID, Vector<HumanResource>>> resourceMap = new ConcurrentHashMap<>();

    private final CitizenQueryService citizenQueryService;
    private final HumanResourceConverter humanResourceConverter;

    public Optional<HumanResource> getOne(UUID gameId, UUID starId, UUID allocation, SkillType requiredSkill) {
        List<HumanResource> availableHumanResources = fetch(gameId, starId)
            .stream()
            //Filter out depleted HumanResources
            .filter(humanResource -> !humanResource.isDepleted())
            .collect(Collectors.toList());
        Optional<HumanResource> allocated = availableHumanResources.stream()
            //Searching for HumanResource already allocated to Producer
            .filter(humanResource -> allocation.equals(humanResource.getAllocation()))
            .findFirst();
        if (allocated.isPresent()) {
            log.info("Allocated HumanResource found: {}", allocated);
            return allocated;
        } else {
            Optional<HumanResource> result = getUnassigned(availableHumanResources, requiredSkill);
            log.info("Unassigned HumanResource found: {}", result);
            return result;
        }
    }

    private Optional<HumanResource> getUnassigned(List<HumanResource> availableHumanResources, SkillType requiredSkill) {
        return availableHumanResources.stream()
            //Filtering free HumanResources
            .filter(humanResource -> isNull(humanResource.getAllocation()))
            //Ordering by productivity
            .max(Comparator.comparing(o -> o.getProductivity(requiredSkill)));
    }

    private List<HumanResource> fetch(UUID gameId, UUID starId) {
        if (!resourceMap.containsKey(gameId)) {
            resourceMap.put(gameId, new ConcurrentHashMap<>());
        }
        Map<UUID, Vector<HumanResource>> resourcesForGame = resourceMap.get(gameId);
        if (!resourcesForGame.containsKey(starId)) {
            log.info("Loading HumanResources for gameId {} and starId {}", gameId, starId);
            resourcesForGame.put(starId, load(starId));
        }
        return resourcesForGame.get(starId);
    }

    private Vector<HumanResource> load(UUID starId) {
        return citizenQueryService.getByLocationIdAndOwnerId(starId)
            .stream()
            .map(humanResourceConverter::convert)
            .collect(Collectors.toCollection(Vector::new));
    }

    public void clear(UUID gameId) {
        //TODO process moral changes
        resourceMap.remove(gameId);
    }
}
