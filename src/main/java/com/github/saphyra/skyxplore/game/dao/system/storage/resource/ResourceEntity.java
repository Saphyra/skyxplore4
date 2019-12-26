package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "resource")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ResourceEntity {
    @Id
    private String resourceId;
    private String gameId;
    private String userId;
    private String playerId;
    @Enumerated(EnumType.STRING)
    private StorageType storageType;
    private String dataId;
    private String starId;
    private Integer amount;
    private Integer round;
}
