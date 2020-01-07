package com.github.saphyra.skyxplore.game.service.system.storage.resource;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.github.saphyra.skyxplore.game.dao.system.storage.resource.ResourceQueryService;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.dao.system.storage.resource.Resource;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActualResourceQueryService {
    private final ResourceQueryService resourceQueryService;

    public List<Resource> getActualsByStarIdAndStorageType(UUID starId, StorageType storageType) {
        List<Resource> resources = resourceQueryService.getByStarIdAndStorageTypeAndPlayerId(starId, storageType);
        Map<String, List<Resource>> resourceMap = map(resources);

        List<Resource> result = new ArrayList<>();
        for (List<Resource> resourceList : resourceMap.values()) {
            result.add(findLastRound(resourceList));
        }
        return result;
    }

    private Map<String, List<Resource>> map(List<Resource> resources) {
        Map<String, List<Resource>> result = new HashMap<>();
        for (Resource resource : resources) {
            if (!result.containsKey(resource.getDataId())) {
                result.put(resource.getDataId(), new ArrayList<>());
            }

            result.get(resource.getDataId()).add(resource);
        }
        return result;
    }

    private Resource findLastRound(List<Resource> resources) {
        Resource result = null;
        for (Resource resource : resources) {
            if (isNull(result) || result.getRound() < resource.getRound()) {
                result = resource;
            }
        }
        return result;
    }
}
