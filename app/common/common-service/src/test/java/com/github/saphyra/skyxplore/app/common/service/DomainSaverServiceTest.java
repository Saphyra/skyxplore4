package com.github.saphyra.skyxplore.app.common.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import com.github.saphyra.skyxplore.app.common.event.EntitiesSavedEvent;
import com.github.saphyra.skyxplore.app.common.game_context.SaveAllDao;


@RunWith(MockitoJUnitRunner.class)
public class DomainSaverServiceTest {
    private static final String VALUE_1 = "value-1";
    private static final String VALUE_2 = "value-2";
    private static final Integer INT_1 = 5;

    @Mock
    private SaveAllDao<String> stringSaveAllDao;

    @Mock
    private SaveAllDao<Integer> integerSaveAllDao;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    private DomainSaverService underTest;

    @Before
    public void setUp() {
        given(stringSaveAllDao.getType()).willReturn(String.class);
        given(integerSaveAllDao.getType()).willReturn(Integer.class);
        underTest = new DomainSaverService(applicationEventPublisher, Arrays.asList(stringSaveAllDao, integerSaveAllDao));
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_collection() {
        underTest.add(new ArrayList<>());
    }

    @Test
    public void add() throws NoSuchFieldException, IllegalAccessException {
        underTest.add(VALUE_1);

        assertThat(getTempStorage().get().get(String.class)).containsExactly(VALUE_1);
    }

    @Test
    public void addAll() throws NoSuchFieldException, IllegalAccessException {
        underTest.addAll(Arrays.asList(VALUE_1, VALUE_2));

        assertThat(getTempStorage().get().get(String.class)).containsExactly(VALUE_1, VALUE_2);
    }

    @Test
    public void addAll_emptyList() throws NoSuchFieldException, IllegalAccessException {
        underTest.addAll(Collections.emptyList());

        assertThat(getTempStorage().get()).isNull();
    }

    @Test
    public void save() throws NoSuchFieldException, IllegalAccessException {
        underTest.add(VALUE_1);
        underTest.add(INT_1);

        underTest.save();

        verify(stringSaveAllDao).saveAll(Arrays.asList(VALUE_1));
        verify(integerSaveAllDao).saveAll(Arrays.asList(INT_1));
        verify(applicationEventPublisher).publishEvent(new EntitiesSavedEvent());

        assertThat(getTempStorage().get()).isNull();
    }

    @Test
    public void save_emptyStorage() throws NoSuchFieldException, IllegalAccessException {
        underTest.save();

        verify(applicationEventPublisher).publishEvent(new EntitiesSavedEvent());
        assertThat(getTempStorage().get()).isNull();
    }

    @Test
    public void clear() throws NoSuchFieldException, IllegalAccessException {
        underTest.add(VALUE_1);
        underTest.add(INT_1);

        underTest.clear();

        assertThat(getTempStorage().get()).isNull();
    }

    private ThreadLocal<Map<Class<?>, List<Object>>> getTempStorage() throws NoSuchFieldException, IllegalAccessException {
        Field field = underTest.getClass().getDeclaredField("tempStorage");
        field.setAccessible(true);
        //noinspection unchecked
        return (ThreadLocal<Map<Class<?>, List<Object>>>) field.get(underTest);
    }
}