package com.github.saphyra.skyxplore.app.domain.user.domain.user;

import com.github.saphyra.authservice.auth.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {
    private static final String USER_NAME = "user-name";

    @Mock
    private UserConverter userConverter;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDao underTest;

    @Mock
    private SkyXpUser skyXpUser;

    @Mock
    private User user;

    @Test
    public void findByUserName() {
        //GIVEN
        given(userRepository.findByUserName(USER_NAME)).willReturn(Optional.of(skyXpUser));
        given(userConverter.convertEntity(Optional.of(skyXpUser))).willReturn(Optional.of(user));
        //WHEN
        Optional<User> result = underTest.findByUserName(USER_NAME);
        //THEN
        assertThat(result).contains(user);
    }
}