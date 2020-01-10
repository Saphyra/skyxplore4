package com.github.saphyra.skyxplore.game.dao.system.citizen;

import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.dao.common.cache.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
@Slf4j
public class CachingSkillRepository extends CacheRepository<String, SkillEntity, String, SkillRepository> implements SkillRepository {
    private final RequestContextHolder requestContextHolder;
    private final UuidConverter uuidConverter;

    protected CachingSkillRepository(SkillRepository repository, RequestContextHolder requestContextHolder, UuidConverter uuidConverter) {
        super(repository, SkillEntity::getGameId);
        this.requestContextHolder = requestContextHolder;
        this.uuidConverter = uuidConverter;
    }

    @Override
    protected List<SkillEntity> getByKey(String gameId) {
        List<SkillEntity> entities = repository.getByGameId(gameId);
        log.info("SkillEntities loaded by gameId {}: {}", gameId, entities.size());
        return entities;
    }

    @Override
    protected void deleteByIds(List<String> skillIds) {
        repository.deleteBySkillIdIn(skillIds);
    }

    @Override
    public void deleteByGameId(String gameId) {
        deleteByKey(gameId);
    }

    @Override
    public void deleteBySkillIdIn(List<String> skillIds) {
        skillIds.forEach(this::deleteById);
    }

    @Override
    public List<SkillEntity> getByCitizenId(String citizenId) {
        return getMapByKey(getGameId())
            .values()
            .stream()
            .filter(skillEntity -> skillEntity.getCitizenId().equals(citizenId))
            .collect(Collectors.toList());
    }

    @Override
    public List<SkillEntity> getByGameId(String gameId) {
        return new ArrayList<>(getMapByKey(gameId).values());
    }

    private String getGameId() {
        return uuidConverter.convertDomain(requestContextHolder.get().getGameId());
    }
}
