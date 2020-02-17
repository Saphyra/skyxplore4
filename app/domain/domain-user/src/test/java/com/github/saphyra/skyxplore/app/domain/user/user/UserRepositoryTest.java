package com.github.saphyra.skyxplore.app.domain.user.user;

import com.github.saphyra.skyxplore.app.common.test.RepositoryTestConfiguration;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RepositoryTestConfiguration.class)
public class UserRepositoryTest {
    private static final String USER_NAME_1 = "user-name-1";
    private static final String USER_NAME_2 = "user-name-2";
    @Autowired
    private UserRepository underTest;

    @After
    public void clear() {
        underTest.deleteAll();
    }

    @Test
    public void findByUserName() {
        //GIVEN
        SkyXpUser user1 = create(USER_NAME_1);
        SkyXpUser user2 = create(USER_NAME_2);
        underTest.save(user1);
        underTest.save(user2);
        //WHEN
        Optional<SkyXpUser> result = underTest.findByUserName(USER_NAME_1);
        //THEN
        assertThat(result).contains(user1);
    }

    private SkyXpUser create(String userName) {
        return SkyXpUser.builder()
            .userId(UUID.randomUUID().toString())
            .userName(userName)
            .password("")
            .build();
    }
}