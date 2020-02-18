package com.github.saphyra.skyxplore.app.auth;

import com.github.saphyra.authservice.auth.domain.User;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.app.domain.user.domain.user.UserDao;
import com.github.saphyra.skyxplore.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.common.utils.UuidConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserQueryServiceTest {
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_ID_STRING = "user-id-string";
    @Mock
    private UserDao userDao;

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private UserQueryService underTest;

    @Mock
    private User user;

    @Test
    public void findByUserIdValidated() {
        //GIVEN
        given(uuidConverter.convertDomain(USER_ID)).willReturn(USER_ID_STRING);
        given(userDao.findById(USER_ID_STRING)).willReturn(Optional.of(user));
        //WHEN
        User result = underTest.findByUserIdValidated(USER_ID);
        //THEN
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void findByUserIdValidated_notFound() {
        //GIVEN
        given(uuidConverter.convertDomain(USER_ID)).willReturn(USER_ID_STRING);
        given(userDao.findById(USER_ID_STRING)).willReturn(Optional.empty());
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.findByUserIdValidated(USER_ID));
        //THEN
        assertThat(ex).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUND.name());
    }
}