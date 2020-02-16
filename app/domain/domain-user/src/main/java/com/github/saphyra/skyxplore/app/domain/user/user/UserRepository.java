package com.github.saphyra.skyxplore.app.domain.user.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//TODO unit test
public interface UserRepository extends JpaRepository<SkyXpUser, String> {
    Optional<SkyXpUser> findByUserName(String userName);
}
