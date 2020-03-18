package com.github.saphyra.skyxplore.app.domain.star;

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
public class StarCommandServiceTest {
    private static final UUID GAME_ID = UUID.randomUUID();

    @Mock
    private StarDao starDao;

    @InjectMocks
    private StarCommandService underTest;

    @Mock
    private Star star;

    @Test
    public void delete() {
        underTest.delete(star);

        verify(starDao).delete(star);
    }

    @Test
    public void deleteAll() {
        underTest.deleteAll(Arrays.asList(star));

        verify(starDao).deleteAll(Arrays.asList(star));
    }

    @Test
    public void deleteByGameId() {
        underTest.deleteByGameId(GAME_ID);

        verify(starDao).deleteByGameId(GAME_ID);
    }

    @Test
    public void save() {
        underTest.save(star);

        verify(starDao).save(star);
    }

    @Test
    public void saveAll() {
        underTest.saveAll(Arrays.asList(star));

        verify(starDao).saveAll(Arrays.asList(star));
    }

    @Test
    public void getType() {
        Class<Star> result = underTest.getType();

        assertThat(result).isEqualTo(Star.class);
    }
}