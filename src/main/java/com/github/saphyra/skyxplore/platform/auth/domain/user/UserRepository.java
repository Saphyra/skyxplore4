package com.github.saphyra.skyxplore.platform.auth.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SkyXpUser, String> {
    Optional<SkyXpUser> findByUserName(String userName);
}
