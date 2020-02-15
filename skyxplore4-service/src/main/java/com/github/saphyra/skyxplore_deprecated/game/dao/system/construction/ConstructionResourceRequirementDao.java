package com.github.saphyra.skyxplore_deprecated.game.dao.system.construction;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.utils.UuidConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
class ConstructionResourceRequirementDao extends AbstractDao<ConstructionResourceRequirementEntity, ConstructionResourceRequirement, String, ConstructionResourceRequirementRepository> {
    private final UuidConverter uuidConverter;

    public ConstructionResourceRequirementDao(Converter<ConstructionResourceRequirementEntity, ConstructionResourceRequirement> converter, ConstructionResourceRequirementRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    void deleteByConstructionId(UUID constructionId) {
        repository.deleteByConstructionId(uuidConverter.convertDomain(constructionId));
    }

    public void deleteByGameId(UUID gameId) {
        log.info("Deleting ConstructionResourceRequirements for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    Map<String, Integer> getByConstructionId(UUID constructionId) {
        return converter.convertEntity(repository.getByConstructionId(uuidConverter.convertDomain(constructionId))).stream()
            .collect(Collectors.toMap(ConstructionResourceRequirement::getResourceId, ConstructionResourceRequirement::getRequiredAmount));
    }
}
