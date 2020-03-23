package com.github.saphyra.skyxplore.app.domain.surface;

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
public class SurfaceCommandServiceTest {
    private static final UUID GAME_ID = UUID.randomUUID();

    @Mock
    private SurfaceDao surfaceDao;

    @InjectMocks
    private SurfaceCommandService underTest;

    @Mock
    private Surface surface;

    @Test
    public void delete() {
        underTest.delete(surface);

        verify(surfaceDao).delete(surface);
    }

    @Test
    public void deleteAll() {
        underTest.deleteAll(Arrays.asList(surface));

        verify(surfaceDao).deleteAll(Arrays.asList(surface));
    }

    @Test
    public void deleteByGameId() {
        underTest.deleteByGameId(GAME_ID);

        verify(surfaceDao).deleteByGameId(GAME_ID);
    }

    @Test
    public void save() {
        underTest.save(surface);

        verify(surfaceDao).save(surface);
    }

    @Test
    public void saveAll() {
        underTest.saveAll(Arrays.asList(surface));

        verify(surfaceDao).saveAll(Arrays.asList(surface));
    }

    @Test
    public void getType() {
        Class<Surface> result = underTest.getType();

        assertThat(result).isEqualTo(Surface.class);
    }
}