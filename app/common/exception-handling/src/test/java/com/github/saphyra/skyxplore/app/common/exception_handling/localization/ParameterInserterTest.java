package com.github.saphyra.skyxplore.app.common.exception_handling.localization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ParameterInserterTest {
    private static final String KEY = "key";
    private static final String ERROR_MESSAGE = String.format("prefix{%s}suffix{%s}", KEY, KEY);
    private static final String REPLACEMENT = "replacement";

    @Mock
    private ParamKeyAssembler paramKeyAssembler;

    @InjectMocks
    private ParameterInserter underTest;

    @Test
    public void insertParams() {
        //GIVEN
        Map<String, String> params = new HashMap<>();
        params.put(KEY, REPLACEMENT);

        given(paramKeyAssembler.assembleKey(KEY)).willReturn(String.format("{%s}", KEY));
        //WHEN
        String result = underTest.insertParams(ERROR_MESSAGE, params);
        //THEN
        assertThat(result).isEqualTo("prefixreplacementsuffixreplacement");
    }
}