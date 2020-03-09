package com.github.saphyra.skyxplore.app.common.dao.cache_repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.OffsetDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.util.OffsetDateTimeProvider;

@RunWith(MockitoJUnitRunner.class)
public class ExpirableEntityTest {
    private static final Object TEST_ENTITY = "test-entity";
    private static final Integer EXPIRATION_SECONDS = 425;
    private static final OffsetDateTime CURRENT_DATE = OffsetDateTime.now();

    @Mock
    private CacheContext cacheContext;

    @Mock
    private OffsetDateTimeProvider offsetDateTimeProvider;

    private ExpirableEntity<Object> underTest;

    @Before
    public void setUp() {
        given(cacheContext.getOffsetDateTimeProvider()).willReturn(offsetDateTimeProvider);
        given(cacheContext.getCacheRepositoryExpirationSeconds()).willReturn(EXPIRATION_SECONDS);
        given(offsetDateTimeProvider.getCurrentDate()).willReturn(CURRENT_DATE);

        underTest = ExpirableEntity.builder()
            .entity(TEST_ENTITY)
            .cacheContext(cacheContext)
            .build();
    }

    @Test
    public void updateLastAccess_update() {
        OffsetDateTime updatedLastAccess = OffsetDateTime.now().plusSeconds(1);
        given(offsetDateTimeProvider.getCurrentDate()).willReturn(updatedLastAccess);

        underTest.updateLastAccess();

        assertThat(underTest.getLastAccess()).isEqualTo(updatedLastAccess);
    }

    @Test
    public void updateLastAccess_shouldNotUpdate() {
        OffsetDateTime updatedLastAccess = OffsetDateTime.now().minusSeconds(1);
        given(offsetDateTimeProvider.getCurrentDate()).willReturn(updatedLastAccess);

        underTest.updateLastAccess();

        assertThat(underTest.getLastAccess()).isEqualTo(CURRENT_DATE);
    }

    @Test
    public void isExpired_expired() {
        given(offsetDateTimeProvider.getCurrentDate()).willReturn(CURRENT_DATE.plusSeconds(EXPIRATION_SECONDS + 1));

        boolean result = underTest.isExpired();

        assertThat(result).isTrue();
    }

    @Test
    public void isExpired_notExpired() {
        given(offsetDateTimeProvider.getCurrentDate()).willReturn(CURRENT_DATE.plusSeconds(EXPIRATION_SECONDS - 1));

        boolean result = underTest.isExpired();

        assertThat(result).isFalse();
    }
}