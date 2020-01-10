package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "resource")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ResourceEntity implements Persistable<String> {
    @Id
    private String resourceId;
    private String gameId;
    private String playerId;
    @Enumerated(EnumType.STRING)
    private StorageType storageType;
    private String dataId;
    private String starId;
    private Integer amount;
    private Integer round;

    @Transient
    private boolean isNew;

    @Override
    public String getId() {
        return resourceId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
