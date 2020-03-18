package com.github.saphyra.skyxplore.app.domain.star;

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
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;

@RunWith(MockitoJUnitRunner.class)
public class StarQueryServiceTest {
    private static final UUID PLAYER_ID = UUID.randomUUID();
    private static final UUID STAR_ID = UUID.randomUUID();
    private static final String STAR_ID_STRING = "star-id";

    @Mock
    private RequestContextHolder requestContextHolder;

    @Mock
    private StarDao starDao;

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private StarQueryService underTest;

    @Mock
    private RequestContext requestContext;

    @Mock
    private Star star;

    @Before
    public void setUp() {
        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getPlayerId()).willReturn(PLAYER_ID);
    }

    @Test
    public void findByStarIdAndOwnerId_found() {
        given(starDao.findByStarIdAndOwnerId(STAR_ID, PLAYER_ID)).willReturn(Optional.of(star));

        Star result = underTest.findByStarIdAndOwnerId(STAR_ID);

        assertThat(result).isEqualTo(star);
    }

    @Test
    public void findByStarIdAndOwnerId_notFound() {
        given(starDao.findByStarIdAndOwnerId(STAR_ID, PLAYER_ID)).willReturn(Optional.empty());

        Throwable ex = catchThrowable(() -> underTest.findByStarIdAndOwnerId(STAR_ID));

        assertThat(ex).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.STAR_NOT_FOUND.name());
    }

    @Test
    public void getByOwnerId_withOwnerId() {
        given(starDao.getByOwnerId(PLAYER_ID)).willReturn(Arrays.asList(star));

        List<Star> result = underTest.getByOwnerId(PLAYER_ID);

        assertThat(result).containsExactly(star);
    }

    @Test
    public void getByOwnerId_withoutOwnerId() {
        given(starDao.getByOwnerId(PLAYER_ID)).willReturn(Arrays.asList(star));

        List<Star> result = underTest.getByOwnerId();

        assertThat(result).containsExactly(star);
    }

    @Test
    public void getCoordinateOfStar() {
        Coordinate coordinate = new Coordinate(23423, 2342);
        given(star.getCoordinate()).willReturn(coordinate);
        given(uuidConverter.convertDomain(STAR_ID)).willReturn(STAR_ID_STRING);
        given(starDao.findById(STAR_ID_STRING)).willReturn(Optional.of(star));

        Coordinate result = underTest.getCoordinateOfStar(STAR_ID);

        assertThat(result).isEqualTo(coordinate);
    }

    @Test
    public void getCoordinateOfStar_notFound() {
        given(uuidConverter.convertDomain(STAR_ID)).willReturn(STAR_ID_STRING);
        given(starDao.findById(STAR_ID_STRING)).willReturn(Optional.empty());

        Throwable ex = catchThrowable(() -> underTest.getCoordinateOfStar(STAR_ID));

        assertThat(ex).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.STAR_NOT_FOUND.name());
    }
}