package com.github.saphyra.skyxplore_deprecated.game.dao.system.construction;

import com.github.saphyra.skyxplore_deprecated.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConstructionRequiredResourcesCommandService implements CommandService<ConstructionResourceRequirement> {
    private final ConstructionResourceRequirementDao constructionResourceRequirementDao;

    @Override
    public void delete(ConstructionResourceRequirement domain) {
        constructionResourceRequirementDao.delete(domain);
    }

    @Override
    public void deleteAll(List<ConstructionResourceRequirement> domains) {
        constructionResourceRequirementDao.deleteAll(domains);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        constructionResourceRequirementDao.deleteByGameId(gameId);
    }

    @Override
    public void save(ConstructionResourceRequirement constructionResourceRequirement) {
        constructionResourceRequirementDao.save(constructionResourceRequirement);
    }

    @Override
    public void saveAll(List<ConstructionResourceRequirement> domains) {
        constructionResourceRequirementDao.saveAll(domains);
    }

    @Override
    public Class<ConstructionResourceRequirement> getType() {
        return ConstructionResourceRequirement.class;
    }
}
