package com.github.saphyra.skyxplore.app.service.game_creation.game;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(MockitoJUnitRunner.class)
public class GameNameValidatorTest {
    @InjectMocks
    private GameNameValidator underTest;

    @Test
    public void blankGameName() {
        Throwable ex = catchThrowable(() -> underTest.validate("     "));

        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.INVALID_REQUEST.name());
        assertThat(exception.getErrorMessage().getParams().get("invalidField")).isEqualTo("gameName");
    }

    @Test
    public void tooShortGameName() {
        Throwable ex = catchThrowable(() -> underTest.validate("as"));

        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.INVALID_REQUEST.name());
        assertThat(exception.getErrorMessage().getParams().get("invalidField")).isEqualTo("gameName");
    }

    @Test
    public void tooLongGameName() {
        Throwable ex = catchThrowable(() -> underTest.validate(Stream.generate(() -> "a").limit(33).collect(Collectors.joining())));

        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.INVALID_REQUEST.name());
        assertThat(exception.getErrorMessage().getParams().get("invalidField")).isEqualTo("gameName");
    }

    @Test
    public void valid() {
        underTest.validate("gameName");
    }
}