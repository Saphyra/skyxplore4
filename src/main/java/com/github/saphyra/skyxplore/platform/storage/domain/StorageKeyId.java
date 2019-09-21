package com.github.saphyra.skyxplore.platform.storage.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StorageKeyId implements Serializable {
    private String userId;

    @Enumerated(EnumType.STRING)
    private StorageKey storageKey;
}
