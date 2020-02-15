package com.github.saphyra.skyxplore_deprecated.platform.storage.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "storage")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Storage {
    @EmbeddedId
    private StorageKeyId storageKey;
    private String value;
}
