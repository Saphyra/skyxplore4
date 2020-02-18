package com.github.saphyra.skyxplore.app.domain.user.domain.accesstoken;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "access_token")
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
class SkyXpAccessToken {
    @Id
    @NonNull
    private String accessTokenId;

    @NonNull
    private String userId;

    @NonNull
    private OffsetDateTime lastAccess;
}
