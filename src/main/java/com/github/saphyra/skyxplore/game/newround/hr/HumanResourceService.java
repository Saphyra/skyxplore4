package com.github.saphyra.skyxplore.game.newround.hr;

import com.github.saphyra.skyxplore.game.dao.system.citizen.CitizenQueryService;
import com.github.saphyra.skyxplore.game.dao.system.citizen.SkillType;
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

    public Optional<HumanResource> getOne(UUID gameId, UUID starId, UUID buildingId, SkillType requiredSkill) {
        List<HumanResource> availableHumanResources = fetch(gameId, starId)
            .stream()
            .filter(humanResource -> !humanResource.isDepleted())
            .collect(Collectors.toList());
        Optional<HumanResource> allocated = availableHumanResources.stream()
            .filter(humanResource -> buildingId.equals(humanResource.getAllocation()))
            .findFirst();
        if (allocated.isPresent()) {
            return allocated;
        } else {
            return getUnassigned(availableHumanResources, requiredSkill);
        }
    }

    private Optional<HumanResource> getUnassigned(List<HumanResource> availableHumanResources, SkillType requiredSkill) {
        return availableHumanResources.stream()
            .filter(humanResource -> isNull(humanResource.getAllocation()))
            .max(Comparator.comparing(o -> o.getProductivity(requiredSkill)));
    }

    private List<HumanResource> fetch(UUID gameId, UUID starId) {
        if (!resourceMap.containsKey(gameId)) {
            resourceMap.put(gameId, new ConcurrentHashMap<>());
        }
        Map<UUID, Vector<HumanResource>> resourcesForGame = resourceMap.get(gameId);
        if (!resourcesForGame.containsKey(starId)) {
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
        resourceMap.remove(gameId);
    }
}
