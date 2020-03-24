package com.github.saphyra.skyxplore.app.service.star;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star.StarCommandService;
import com.github.saphyra.skyxplore.app.domain.star.StarQueryService;

@RunWith(MockitoJUnitRunner.class)
public class RenameStarServiceTest {
    private static final UUID STAR_ID = UUID.randomUUID();
    private static final String STAR_NAME = "star-name";

    @Mock
    private StarCommandService starCommandService;

    @Mock
    private StarQueryService starQueryService;

    @InjectMocks
    private RenameStarService underTest;

    @Mock
    private Star star;

    @Test
    public void tooShortStarName() {
        Throwable ex = catchThrowable(() -> underTest.rename(STAR_ID, "ab"));

        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.INVALID_STAR_NAME.name());
    }

    @Test
    public void tooLongStarName() {
        Throwable ex = catchThrowable(() -> underTest.rename(STAR_ID, Stream.generate(() -> "a").limit(31).collect(Collectors.joining())));

        assertThat(ex).isInstanceOf(BadRequestException.class);
        BadRequestException exception = (BadRequestException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.INVALID_STAR_NAME.name());
    }

    @Test
    public void rename() {
        given(starQueryService.findByStarIdAndOwnerId(STAR_ID)).willReturn(star);

        underTest.rename(STAR_ID, STAR_NAME);

        verify(star).setStarName(STAR_NAME);
        verify(starCommandService).save(star);
    }
}