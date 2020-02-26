package com.github.saphyra.skyxplore.app.common.exception_handling.localization;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParamKeyAssemblerTest {
    private static final String KEY = "key";
    @InjectMocks
    private ParamKeyAssembler underTest;

    @Test
    public void assembleKey() {
        //WHEN
        String result = underTest.assembleKey(KEY);
        //THEN
        assertThat(result).isEqualTo(String.format("${%s}", KEY));
    }
}