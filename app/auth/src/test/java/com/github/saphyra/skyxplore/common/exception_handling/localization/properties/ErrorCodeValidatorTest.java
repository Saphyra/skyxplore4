package com.github.saphyra.skyxplore.common.exception_handling.localization.properties;

import com.github.saphyra.skyxplore.common.exception_handling.ErrorCode;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class ErrorCodeValidatorTest {
    private static final String LOCALE = "locale";
    private static final String LOCALIZATION = "localization";
    private static final String UNKNOWN_KEY = "unknown-key";

    @InjectMocks
    private ErrorCodeValidator underTest;

    @Test(expected = IllegalStateException.class)
    public void emptyErrorCodeLocalization() {
        //GIVEN
        Map<String, ErrorCodeLocalization> errorCodeLocalizationMap = new HashMap<>();
        errorCodeLocalizationMap.put(LOCALE, new ErrorCodeLocalization());
        //WHEN
        underTest.validate(errorCodeLocalizationMap);
    }

    @Test(expected = IllegalStateException.class)
    public void localizationNotPresentForErrorCode() {
        //GIVEN
        Map<String, ErrorCodeLocalization> errorCodeLocalizationMap = new HashMap<>();
        ErrorCodeLocalization errorCodeLocalization = createFilledLocalizationMap();
        errorCodeLocalization.remove(ErrorCode.BAD_CREDENTIALS.name());
        errorCodeLocalizationMap.put(LOCALE, errorCodeLocalization);
        //WHEN
        underTest.validate(errorCodeLocalizationMap);
    }

    @Test(expected = IllegalStateException.class)
    public void localizationEmptyForErrorCode() {
        //GIVEN
        Map<String, ErrorCodeLocalization> errorCodeLocalizationMap = new HashMap<>();
        ErrorCodeLocalization errorCodeLocalization = createFilledLocalizationMap();
        errorCodeLocalization.put(ErrorCode.BAD_CREDENTIALS.name(), "");
        errorCodeLocalizationMap.put(LOCALE, errorCodeLocalization);
        //WHEN
        underTest.validate(errorCodeLocalizationMap);
    }

    @Test(expected = IllegalStateException.class)
    @Ignore //TODO restore when ready
    public void errorCodeNotPresentForLocalization() {
        //GIVEN
        Map<String, ErrorCodeLocalization> errorCodeLocalizationMap = new HashMap<>();
        ErrorCodeLocalization errorCodeLocalization = createFilledLocalizationMap();
        errorCodeLocalization.put(UNKNOWN_KEY, LOCALIZATION);
        errorCodeLocalizationMap.put(LOCALE, errorCodeLocalization);
        //WHEN
        underTest.validate(errorCodeLocalizationMap);
    }

    @Test
    public void valid() {
        //GIVEN
        Map<String, ErrorCodeLocalization> errorCodeLocalizationMap = new HashMap<>();
        ErrorCodeLocalization errorCodeLocalization = createFilledLocalizationMap();
        errorCodeLocalizationMap.put(LOCALE, errorCodeLocalization);
        //WHEN
        underTest.validate(errorCodeLocalizationMap);
    }

    private ErrorCodeLocalization createFilledLocalizationMap() {
        Map<String, String> localizations = Arrays.stream(ErrorCode.values())
            .collect(Collectors.toMap(Enum::name, o -> LOCALIZATION));

        ErrorCodeLocalization errorCodeLocalization = new ErrorCodeLocalization();
        errorCodeLocalization.putAll(localizations);
        return errorCodeLocalization;
    }
}