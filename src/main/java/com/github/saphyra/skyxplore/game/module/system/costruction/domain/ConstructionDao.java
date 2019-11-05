package com.github.saphyra.skyxplore.game.module.system.costruction.domain;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;

@Component
public class ConstructionDao extends AbstractDao<ConstructionEntity, Construction, String, ConstructionRepository> {
    public ConstructionDao(Converter<ConstructionEntity, Construction> converter, ConstructionRepository repository) {
        super(converter, repository);
    }
}
