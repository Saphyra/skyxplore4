package com.github.saphyra.skyxplore.game.module.system.resource;

import com.github.saphyra.skyxplore.game.module.system.resource.domain.Resource;
import com.github.saphyra.skyxplore.game.module.system.resource.domain.ResourceDao;
import com.github.saphyra.skyxplore.game.module.system.resource.domain.StorageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class ResourceQueryService {
    private final ResourceDao resourceDao;

    public List<Resource> getActualsByStarIdAndStorageType(UUID starId, StorageType storageType) {
        List<Resource> resources = resourceDao.getByStarIdAndStorageType(starId, storageType);
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

    public Optional<Resource> findByStarIdDataIdAndRound(UUID starId, String dataId, int round) {
        return resourceDao.findByStarIdAndDataIdAndRound(starId, dataId, round);
    }

    public List<Resource> getByStarIdAndDataId(UUID starId, String dataId) {
        return resourceDao.getByStarIdAndDataId(starId, dataId);
    }
}
