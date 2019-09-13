package com.github.saphyra.skyxplore.platform.storage.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user_settings")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Storage {
    @EmbeddedId
    private StorageKeyId storageKey;

    @Type(type = "text")
    private String value;
}
