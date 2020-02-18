package com.github.saphyra.skyxplore.app.domain.user.domain.user;

import com.github.saphyra.authservice.auth.domain.Credentials;
import com.github.saphyra.authservice.auth.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterTest {
    private static final String USER_ID = "user-id";
    private static final String PASSWORD = "password";
    private static final String USER_NAME = "user-name";

    @InjectMocks
    private UserConverter underTest;

    @Test
    public void convertEntity() {
        //GIVEN
        SkyXpUser user = SkyXpUser.builder()
            .userId(USER_ID)
            .userName(USER_NAME)
            .password(PASSWORD)
            .build();
        //WHEN
        User result = underTest.convertEntity(user);
        //THEN
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getCredentials().getUserName()).isEqualTo(USER_NAME);
        assertThat(result.getCredentials().getPassword()).isEqualTo(PASSWORD);
        assertThat(result.getRoles()).isNotNull();
    }

    @Test
    public void convertDomain() {
        //GIVEN
        User user = User.builder()
            .userId(USER_ID)
            .credentials(
                Credentials.builder()
                    .userName(USER_NAME)
                    .password(PASSWORD)
                    .build()
            )
            .build();
        //WHEN
        SkyXpUser result = underTest.convertDomain(user);
        //THEN
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getUserName()).isEqualTo(USER_NAME);
        assertThat(result.getPassword()).isEqualTo(PASSWORD);
    }
}