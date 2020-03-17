package com.github.saphyra.skyxplore.app.domain.research;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ResearchCommandServiceTest {
    private static final UUID GAME_ID = UUID.randomUUID();

    @Mock
    private ResearchDao researchDao;

    @InjectMocks
    private ResearchCommandService underTest;

    @Mock
    private Research research;

    @Test
    public void delete() {
        underTest.delete(research);

        verify(researchDao).delete(research);
    }

    @Test
    public void deleteAll() {
        underTest.deleteAll(Arrays.asList(research));

        verify(researchDao).deleteAll(Arrays.asList(research));
    }

    @Test
    public void deleteByGameId() {
        underTest.deleteByGameId(GAME_ID);

        verify(researchDao).deleteByGameId(GAME_ID);
    }

    @Test
    public void save() {
        underTest.save(research);

        verify(researchDao).save(research);
    }

    @Test
    public void saveAll() {
        underTest.saveAll(Arrays.asList(research));

        verify(researchDao).saveAll(Arrays.asList(research));
    }

    @Test
    public void getType() {
        Class<Research> result = underTest.getType();

        assertThat(result).isEqualTo(Research.class);
    }
}