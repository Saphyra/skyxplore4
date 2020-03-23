package com.github.saphyra.skyxplore.app.domain.surface;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;

@RunWith(MockitoJUnitRunner.class)
public class SurfaceQueryServiceTest {
    private static final UUID PLAYER_ID = UUID.randomUUID();
    private static final UUID SURFACE_ID = UUID.randomUUID();
    private static final UUID STAR_ID = UUID.randomUUID();

    @Mock
    private RequestContextHolder requestContextHolder;

    @Mock
    private SurfaceDao surfaceDao;

    @InjectMocks
    private SurfaceQueryService underTest;

    @Mock
    private RequestContext requestContext;

    @Mock
    private Surface surface;

    @Before
    public void setUp() {
        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getPlayerId()).willReturn(PLAYER_ID);
    }

    @Test
    public void findBySurfaceIdAndPlayerId_notFound() {
        given(surfaceDao.findBySurfaceIdAndPlayerId(SURFACE_ID, PLAYER_ID)).willReturn(Optional.empty());

        Throwable ex = catchThrowable(() -> underTest.findBySurfaceIdAndPlayerId(SURFACE_ID));

        assertThat(ex).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.SURFACE_NOT_FOUND.name());
    }

    @Test
    public void findBySurfaceIdAndPlayerId() {
        given(surfaceDao.findBySurfaceIdAndPlayerId(SURFACE_ID, PLAYER_ID)).willReturn(Optional.of(surface));

        Surface result = underTest.findBySurfaceIdAndPlayerId(SURFACE_ID);

        assertThat(result).isEqualTo(surface);
    }

    @Test
    public void getByStarIdAndPlayerId() {
        given(surfaceDao.getByStarIdAndPlayerId(STAR_ID, PLAYER_ID)).willReturn(Arrays.asList(surface));

        List<Surface> result = underTest.getByStarIdAndPlayerId(STAR_ID);

        assertThat(result).containsExactly(surface);
    }
}