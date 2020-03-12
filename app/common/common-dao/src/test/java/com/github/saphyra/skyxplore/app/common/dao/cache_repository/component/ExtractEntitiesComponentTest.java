package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.EntityMapping;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ModifiableEntity;

@RunWith(MockitoJUnitRunner.class)
public class ExtractEntitiesComponentTest {
    private static final UUID ENTITY_ID = UUID.randomUUID();

    @InjectMocks
    private ExtractEntitiesComponent underTest;

    @Test
    public void extractEntities() {
        EntityStub entityStub = EntityStub.builder().build();

        EntityMapping<UUID, EntityStub> mapping = new EntityMapping<>();
        mapping.put(ENTITY_ID, new ModifiableEntity<>(entityStub, false));


        Map<UUID, EntityStub> result = underTest.extractEntities(mapping);

        assertThat(result.get(ENTITY_ID)).isEqualTo(entityStub);
    }
}