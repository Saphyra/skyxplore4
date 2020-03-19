package com.github.saphyra.skyxplore.app.auth.error_handler;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.github.saphyra.authservice.auth.domain.AccessStatus;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import lombok.RequiredArgsConstructor;

@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class ErrorCodeResolverTest {
    private ErrorCodeResolver underTest;

    private final AccessStatus accessStatus;
    private final ErrorCode expectedErrorCode;

    @Before
    public void init() {
        underTest = new ErrorCodeResolver();
    }

    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][]{
            {AccessStatus.LOGIN_FAILED, ErrorCode.BAD_CREDENTIALS},
            {AccessStatus.ACCESS_TOKEN_EXPIRED, ErrorCode.SESSION_EXPIRED},
            {AccessStatus.ACCESS_TOKEN_NOT_FOUND, ErrorCode.SESSION_EXPIRED},
            {AccessStatus.COOKIE_NOT_FOUND, ErrorCode.SESSION_EXPIRED},
            {AccessStatus.INVALID_USER_ID, ErrorCode.SESSION_EXPIRED},
            {AccessStatus.USER_NOT_FOUND, ErrorCode.SESSION_EXPIRED},
            {AccessStatus.FORBIDDEN, ErrorCode.GENERAL_ERROR},
            {AccessStatus.GRANTED, ErrorCode.GENERAL_ERROR}
        });
    }

    @Test
    public void getErrorCode() {
        //WHEN
        ErrorCode result = underTest.getErrorCode(accessStatus);
        //THEN
        assertThat(result).isEqualTo(expectedErrorCode);
    }
}