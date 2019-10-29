package com.github.saphyra.skyxplore.game.module.system.resource.domain;

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
    @Enumerated(EnumType.STRING)
    private StorageType storageType;
    private String dataId;
    private String starId;
    private Integer amount;
    private Integer round;
}
