package com.github.saphyra.skyxplore.game.dao.system.construction;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
//TODO make package-private
public class ConstructionDao extends AbstractDao<ConstructionEntity, Construction, String, ConstructionRepository> {
    private final UuidConverter uuidConverter;

    public ConstructionDao(ConstructionConverter converter, ConstructionRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        );
    }

    public Optional<Construction> findByConstructionTypeAndExternalId(ConstructionType constructionType, UUID externalId) {
        return converter.convertEntity(repository.findByConstructionTypeAndExternalId(
            constructionType,
            uuidConverter.convertDomain(externalId)
        ));
    }

    public Optional<Construction> findByConstructionTypeAndSurfaceId(ConstructionType constructionType, UUID surfaceId) {
        return converter.convertEntity(
            repository.findByConstructionTypeAndSurfaceId(
                constructionType,
                uuidConverter.convertDomain(surfaceId)
            )
        );
    }

    public List<Construction> getByStarId(UUID starId) {
        return converter.convertEntity(repository.getByStarId(uuidConverter.convertDomain(starId)));
    }
}
