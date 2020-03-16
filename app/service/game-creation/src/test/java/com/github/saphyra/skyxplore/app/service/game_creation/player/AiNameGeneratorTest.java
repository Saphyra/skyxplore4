package com.github.saphyra.skyxplore.app.service.game_creation.player;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.data.name.FirstNames;
import com.github.saphyra.skyxplore.app.domain.data.name.LastNames;

@RunWith(MockitoJUnitRunner.class)
public class AiNameGeneratorTest {
    private static final String USED_NAME = "name used";
    @Mock
    private FirstNames firstNames;

    @Mock
    private LastNames lastNames;

    @InjectMocks
    private AiNameGenerator underTest;

    @Test
    public void generateName() {
        given(firstNames.getRandom()).willReturn("used")
            .willReturn("user");
        given(lastNames.getRandom()).willReturn("name");

        String result = underTest.generateName(Arrays.asList(USED_NAME));

        assertThat(result).isEqualTo("name user");
    }
}