package com.github.saphyra.skyxplore.platform.auth.domain.accesstoken;

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
import java.util.UUID;

@Entity
@Table(name = "access_token")
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
class SkyXpAccessToken {
    @Id
    @NonNull
    private UUID accessTokenId;

    @NonNull
    private UUID userId;

    @NonNull
    private OffsetDateTime lastAccess;
}
