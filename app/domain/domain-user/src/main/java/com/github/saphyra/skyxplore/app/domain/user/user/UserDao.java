package com.github.saphyra.skyxplore.app.domain.user.user;

import com.github.saphyra.authservice.auth.domain.User;
import com.github.saphyra.dao.AbstractDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDao extends AbstractDao<SkyXpUser, User, String, UserRepository> {
    public UserDao(UserConverter converter, UserRepository repository) {
        super(converter, repository);
    }

    public Optional<User> findByUserName(String userName) {
        return converter.convertEntity(repository.findByUserName(userName));
    }
}
