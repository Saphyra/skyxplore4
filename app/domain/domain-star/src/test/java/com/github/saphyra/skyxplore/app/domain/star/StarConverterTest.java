package com.github.saphyra.skyxplore.app.domain.star;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.coordinate.CoordinateConverter;
import com.github.saphyra.skyxplore.app.domain.coordinate.CoordinateEntity;

@RunWith(MockitoJUnitRunner.class)
public class StarConverterTest {
    private static final String STAR_ID_STRING = "star-id";
    private static final String GAME_ID_STRING = "game-id";
    private static final String ENCRYPTED_STAR_NAME = "encrypted-star-name";
    private static final String DECRYPTED_STAR_NAME = "decrypted-star-name";
    private static final Integer X = 234;
    private static final Integer Y = 254;
    private static final String PLAYER_ID_STRING = "player-id";
    private static final UUID STAR_ID = UUID.randomUUID();
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID PLAYER_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_ID_STRING = "user-id";

    @Mock
    private CoordinateConverter coordinateConverter;

    @Mock
    private RequestContextHolder requestContextHolder;

    @Mock
    private StringEncryptor stringEncryptor;

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private StarConverter underTest;

    @Mock
    private RequestContext requestContext;

    @Before
    public void setUp() {
        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getUserId()).willReturn(USER_ID);
        given(uuidConverter.convertDomain(USER_ID)).willReturn(USER_ID_STRING);
    }

    @Test
    public void convertEntity() {
        CoordinateEntity coordinateEntity = new CoordinateEntity(X, Y);
        StarEntity entity = StarEntity.builder()
            .starId(STAR_ID_STRING)
            .gameId(GAME_ID_STRING)
            .starName(ENCRYPTED_STAR_NAME)
            .coordinates(coordinateEntity)
            .ownerId(PLAYER_ID_STRING)
            .isNew(true)
            .build();
        Coordinate coordinate = new Coordinate(X, Y);

        given(uuidConverter.convertEntity(STAR_ID_STRING)).willReturn(STAR_ID);
        given(uuidConverter.convertEntity(GAME_ID_STRING)).willReturn(GAME_ID);
        given(uuidConverter.convertEntity(PLAYER_ID_STRING)).willReturn(PLAYER_ID);
        given(stringEncryptor.decryptEntity(ENCRYPTED_STAR_NAME, USER_ID_STRING)).willReturn(DECRYPTED_STAR_NAME);
        given(coordinateConverter.convertEntity(coordinateEntity)).willReturn(coordinate);


        Star result = underTest.convertEntity(entity);

        assertThat(result.getStarId()).isEqualTo(STAR_ID);
        assertThat(result.getGameId()).isEqualTo(GAME_ID);
        assertThat(result.getOwnerId()).isEqualTo(PLAYER_ID);
        assertThat(result.getStarName()).isEqualTo(DECRYPTED_STAR_NAME);
        assertThat(result.getCoordinate()).isEqualTo(coordinate);
        assertThat(result.isNew()).isFalse();
    }

    @Test
    public void convertDomain() {
        CoordinateEntity coordinateEntity = new CoordinateEntity(X, Y);
        Coordinate coordinate = new Coordinate(X, Y);
        Star domain = Star.builder()
            .starId(STAR_ID)
            .gameId(GAME_ID)
            .starName(DECRYPTED_STAR_NAME)
            .coordinate(coordinate)
            .ownerId(PLAYER_ID)
            .isNew(true)
            .build();


        given(uuidConverter.convertDomain(STAR_ID)).willReturn(STAR_ID_STRING);
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);
        given(uuidConverter.convertDomain(PLAYER_ID)).willReturn(PLAYER_ID_STRING);
        given(stringEncryptor.encryptEntity(DECRYPTED_STAR_NAME, USER_ID_STRING)).willReturn(ENCRYPTED_STAR_NAME);
        given(coordinateConverter.convertDomain(coordinate)).willReturn(coordinateEntity);


        StarEntity result = underTest.convertDomain(domain);

        assertThat(result.getStarId()).isEqualTo(STAR_ID_STRING);
        assertThat(result.getGameId()).isEqualTo(GAME_ID_STRING);
        assertThat(result.getOwnerId()).isEqualTo(PLAYER_ID_STRING);
        assertThat(result.getStarName()).isEqualTo(ENCRYPTED_STAR_NAME);
        assertThat(result.getCoordinates()).isEqualTo(coordinateEntity);
        assertThat(result.isNew()).isTrue();
    }
}